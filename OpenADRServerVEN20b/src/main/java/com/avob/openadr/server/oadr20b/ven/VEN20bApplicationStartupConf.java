package com.avob.openadr.server.oadr20b.ven;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jxmpp.stringprep.XmppStringprepException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import com.avob.openadr.model.oadr20b.builders.Oadr20bEiReportBuilders;
import com.avob.openadr.model.oadr20b.ei.ReadingTypeEnumeratedType;
import com.avob.openadr.model.oadr20b.exception.Oadr20bException;
import com.avob.openadr.model.oadr20b.exception.Oadr20bHttpLayerException;
import com.avob.openadr.model.oadr20b.exception.Oadr20bMarshalException;
import com.avob.openadr.model.oadr20b.exception.Oadr20bXMLSignatureException;
import com.avob.openadr.model.oadr20b.exception.Oadr20bXMLSignatureValidationException;
import com.avob.openadr.model.oadr20b.oadr.OadrCreateReportType;
import com.avob.openadr.model.oadr20b.oadr.OadrCreatedPartyRegistrationType;
import com.avob.openadr.model.oadr20b.oadr.OadrRegisterReportType;
import com.avob.openadr.model.oadr20b.oadr.OadrReportRequestType;
import com.avob.openadr.server.oadr20b.ven.service.Oadr20bPollService;
import com.avob.openadr.server.oadr20b.ven.service.Oadr20bVENEiRegisterPartyService;
import com.avob.openadr.server.oadr20b.ven.service.Oadr20bVENEiRegisterPartyService.Oadr20bVENEiRegisterPartyServiceListener;
import com.avob.openadr.server.oadr20b.ven.service.Oadr20bVENEiReportService;
import com.avob.openadr.server.oadr20b.ven.service.PlanRequestService;

@Configuration
@ConditionalOnProperty(name = "ven.autostart")
public class VEN20bApplicationStartupConf implements Oadr20bVENEiRegisterPartyServiceListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(VEN20bApplicationStartupConf.class);

	@Resource
	private Oadr20bPollService oadr20bPollService;

	@Resource
	private VenConfig venConfig;

	@Resource
	private MultiVtnConfig multiVtnConfig;

	@Resource
	@Qualifier("reportService")
	private Oadr20bVENEiReportService reportService;

	@Resource
	private PlanRequestService planRequestService;

	@Resource
	private Oadr20bVENEiRegisterPartyService oadr20bVENEiRegisterPartyService;

	@PostConstruct
	public void init() {
		oadr20bVENEiRegisterPartyService.addListener(this);
	}

	@EventListener({ ApplicationReadyEvent.class })
	void applicationReadyEvent() {
		for (VtnSessionConfiguration vtnSessionConfiguration : multiVtnConfig.getMultiConfig().values()) {
			if (oadr20bVENEiRegisterPartyService != null) {
				oadr20bVENEiRegisterPartyService.initRegistration(vtnSessionConfiguration);
			}
		}
	}

	@Override
	public void onRegistrationSuccess(VtnSessionConfiguration vtnConfiguration,
			OadrCreatedPartyRegistrationType registration) {

		oadr20bPollService.initPoll(vtnConfiguration);
		try {
			sendRegisterReportPaylad(vtnConfiguration);
		} catch (XmppStringprepException e) {
			LOGGER.error("", e);
		} catch (NotConnectedException e) {
			LOGGER.error("", e);
		} catch (Oadr20bException e) {
			LOGGER.error("", e);
		} catch (Oadr20bHttpLayerException e) {
			LOGGER.error("", e);
		} catch (Oadr20bXMLSignatureException e) {
			LOGGER.error("", e);
		} catch (Oadr20bXMLSignatureValidationException e) {
			LOGGER.error("", e);
		} catch (Oadr20bMarshalException e) {
			LOGGER.error("", e);
		} catch (InterruptedException e) {
			LOGGER.error("", e);
		}
	}

	private void sendRegisterReportPaylad(VtnSessionConfiguration vtnConfiguration) throws XmppStringprepException,
			NotConnectedException, Oadr20bException, Oadr20bHttpLayerException, Oadr20bXMLSignatureException,
			Oadr20bXMLSignatureValidationException, Oadr20bMarshalException, InterruptedException {
		String requestId = "0";
		String reportRequestId = "0";
		OadrRegisterReportType payload = reportService.selfOadrRegisterReport(requestId, venConfig.getVenId(),
				vtnConfiguration.getVtnId(), reportRequestId);

		multiVtnConfig.oadrRegisterReport(vtnConfiguration, payload);

		String reportSpecifierId = "METADATA";
		String granularity = "P0D";
		String reportBackDuration = "P0D";

		OadrReportRequestType oadrReportRequestType = Oadr20bEiReportBuilders
				.newOadr20bReportRequestTypeBuilder(reportRequestId, reportSpecifierId, granularity, reportBackDuration)
				.addSpecifierPayload(null, ReadingTypeEnumeratedType.DIRECT_READ, reportSpecifierId).build();
		OadrCreateReportType createReport = Oadr20bEiReportBuilders
				.newOadr20bCreateReportBuilder(requestId, vtnConfiguration.getVenSessionConfig().getVenId())
				.addReportRequest(oadrReportRequestType).build();

		multiVtnConfig.oadrCreateReport(vtnConfiguration, createReport);

	}

	@Override
	public void onRegistrationError(VtnSessionConfiguration vtnConfiguration) {
		LOGGER.error("Failed to create party registration");
	}

}
