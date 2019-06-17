package com.avob.openadr.client.http.oadr20b.vtn;

import com.avob.openadr.client.http.oadr20b.OadrHttpClient20b;
import com.avob.openadr.model.oadr20b.Oadr20bFactory;
import com.avob.openadr.model.oadr20b.Oadr20bUrlPath;
import com.avob.openadr.model.oadr20b.exception.Oadr20bException;
import com.avob.openadr.model.oadr20b.exception.Oadr20bHttpLayerException;
import com.avob.openadr.model.oadr20b.exception.Oadr20bXMLSignatureException;
import com.avob.openadr.model.oadr20b.exception.Oadr20bXMLSignatureValidationException;
import com.avob.openadr.model.oadr20b.oadr.OadrCancelOptType;
import com.avob.openadr.model.oadr20b.oadr.OadrCancelPartyRegistrationType;
import com.avob.openadr.model.oadr20b.oadr.OadrCancelReportType;
import com.avob.openadr.model.oadr20b.oadr.OadrCanceledOptType;
import com.avob.openadr.model.oadr20b.oadr.OadrCanceledPartyRegistrationType;
import com.avob.openadr.model.oadr20b.oadr.OadrCanceledReportType;
import com.avob.openadr.model.oadr20b.oadr.OadrCreateOptType;
import com.avob.openadr.model.oadr20b.oadr.OadrCreateReportType;
import com.avob.openadr.model.oadr20b.oadr.OadrCreatedOptType;
import com.avob.openadr.model.oadr20b.oadr.OadrCreatedReportType;
import com.avob.openadr.model.oadr20b.oadr.OadrDistributeEventType;
import com.avob.openadr.model.oadr20b.oadr.OadrRegisterReportType;
import com.avob.openadr.model.oadr20b.oadr.OadrRegisteredReportType;
import com.avob.openadr.model.oadr20b.oadr.OadrRequestReregistrationType;
import com.avob.openadr.model.oadr20b.oadr.OadrResponseType;
import com.avob.openadr.model.oadr20b.oadr.OadrUpdateReportType;
import com.avob.openadr.model.oadr20b.oadr.OadrUpdatedReportType;

public class OadrHttpVtnClient20b {

	private OadrHttpClient20b client;

	public OadrHttpVtnClient20b(OadrHttpClient20b client) {
		this.client = client;
	}

	public OadrResponseType oadrDistributeEvent(String url, OadrDistributeEventType event) throws Oadr20bException,
			Oadr20bHttpLayerException, Oadr20bXMLSignatureException, Oadr20bXMLSignatureValidationException {
		return client.post(url, Oadr20bUrlPath.EI_EVENT_SERVICE, null, Oadr20bFactory.createOadrDistributeEvent(event),
				OadrResponseType.class);
	}

	public OadrCanceledPartyRegistrationType oadrCancelPartyRegistrationType(String url,
			OadrCancelPartyRegistrationType payload) throws Oadr20bException, Oadr20bHttpLayerException,
			Oadr20bXMLSignatureException, Oadr20bXMLSignatureValidationException {
		return client.post(url, Oadr20bUrlPath.EI_REGISTER_PARTY_SERVICE, null,
				Oadr20bFactory.createOadrCancelPartyRegistration(payload), OadrCanceledPartyRegistrationType.class);
	}

	public OadrResponseType oadrRequestReregistrationType(String url, OadrRequestReregistrationType payload)
			throws Oadr20bException, Oadr20bHttpLayerException, Oadr20bXMLSignatureException,
			Oadr20bXMLSignatureValidationException {
		return client.post(url, Oadr20bUrlPath.EI_REGISTER_PARTY_SERVICE, null,
				Oadr20bFactory.createOadrRequestReregistration(payload), OadrResponseType.class);
	}

	public OadrResponseType oadrCreatedReport(String url, OadrCreatedReportType payload) throws Oadr20bException,
			Oadr20bHttpLayerException, Oadr20bXMLSignatureException, Oadr20bXMLSignatureValidationException {
		return client.post(url, Oadr20bUrlPath.EI_REPORT_SERVICE, null, Oadr20bFactory.createOadrCreatedReport(payload),
				OadrResponseType.class);
	}

	public OadrCreatedReportType oadrCreateReport(String url, OadrCreateReportType payload) throws Oadr20bException,
			Oadr20bHttpLayerException, Oadr20bXMLSignatureException, Oadr20bXMLSignatureValidationException {
		return client.post(url, Oadr20bUrlPath.EI_REPORT_SERVICE, null, Oadr20bFactory.createOadrCreateReport(payload),
				OadrCreatedReportType.class);
	}

	public OadrUpdatedReportType oadrUpdateReport(String url, OadrUpdateReportType payload) throws Oadr20bException,
			Oadr20bHttpLayerException, Oadr20bXMLSignatureException, Oadr20bXMLSignatureValidationException {
		return client.post(url, Oadr20bUrlPath.EI_REPORT_SERVICE, null, Oadr20bFactory.createOadrUpdateReport(payload),
				OadrUpdatedReportType.class);
	}

	public OadrCanceledReportType oadrCancelReport(String url, OadrCancelReportType payload) throws Oadr20bException,
			Oadr20bHttpLayerException, Oadr20bXMLSignatureException, Oadr20bXMLSignatureValidationException {
		return client.post(url, Oadr20bUrlPath.EI_REPORT_SERVICE, null, Oadr20bFactory.createOadrCancelReport(payload),
				OadrCanceledReportType.class);
	}

	public OadrCreateOptType oadrCreatedOpt(String url, OadrCreatedOptType payload) throws Oadr20bException,
			Oadr20bHttpLayerException, Oadr20bXMLSignatureException, Oadr20bXMLSignatureValidationException {
		return client.post(url, Oadr20bUrlPath.EI_OPT_SERVICE, null, Oadr20bFactory.createOadrCreatedOpt(payload),
				OadrCreateOptType.class);
	}

	public OadrCancelOptType oadrCanceledOptType(String url, OadrCanceledOptType payload) throws Oadr20bException,
			Oadr20bHttpLayerException, Oadr20bXMLSignatureException, Oadr20bXMLSignatureValidationException {
		return client.post(url, Oadr20bUrlPath.EI_OPT_SERVICE, null, Oadr20bFactory.createOadrCanceledOpt(payload),
				OadrCancelOptType.class);
	}

	public OadrRegisteredReportType oadrRegisterReport(String url, OadrRegisterReportType payload)
			throws Oadr20bException, Oadr20bHttpLayerException, Oadr20bXMLSignatureException,
			Oadr20bXMLSignatureValidationException {
		return client.post(url, Oadr20bUrlPath.EI_REPORT_SERVICE, null,
				Oadr20bFactory.createOadrRegisterReport(payload), OadrRegisteredReportType.class);
	}

}
