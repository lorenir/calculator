package com.principal.calculator.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.principal.calculator.shared.domain.BinaryConversionRegister;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface ConversionService extends RemoteService {
	String conversion(Long decimal) throws IllegalArgumentException;
	List<BinaryConversionRegister> conversionHistory() throws IllegalArgumentException;

}
