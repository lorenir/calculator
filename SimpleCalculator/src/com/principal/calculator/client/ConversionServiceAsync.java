package com.principal.calculator.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.principal.calculator.shared.domain.BinaryConversionRegister;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface ConversionServiceAsync {
	void conversion(Long decimal, AsyncCallback<String> callback) throws IllegalArgumentException;

	void conversionHistory(AsyncCallback<List<BinaryConversionRegister>> callback) throws IllegalArgumentException;
}
