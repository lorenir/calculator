package com.principal.calculator.server;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.principal.calculator.client.ConversionService;
import com.principal.calculator.server.dao.BinaryConversionRegisterDAO;
import com.principal.calculator.shared.domain.BinaryConversionRegister;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class ConversionServiceImpl extends RemoteServiceServlet implements ConversionService {
	private final BinaryConversionRegisterDAO bcrDAO = new BinaryConversionRegisterDAO();
	private Logger log;


	@Override
	public String conversion(Long decimal) throws IllegalArgumentException {
		
		String binary = null;
		try {
			binary = Long.toBinaryString(decimal);
			Date conversionDate = new Date();
			BinaryConversionRegister bcr = new BinaryConversionRegister(decimal.toString(), binary,conversionDate);
			bcrDAO.addRegister(bcr);
		} catch (Exception e) {
			if (log == null){
				log = Logger.getGlobal();
			}
			log.warning("Problem saving in DB. Trace: " + e.getMessage());
		}
		return binary != null ? binary : "ERROR";
	}

	@Override
	public List<BinaryConversionRegister> conversionHistory() throws IllegalArgumentException {
		return bcrDAO.findAllRegisters();
	}
}
