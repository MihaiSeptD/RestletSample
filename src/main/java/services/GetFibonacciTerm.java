package services;

import java.math.BigInteger;
import java.util.List;

import org.apache.log4j.Logger;
import org.restlet.Request;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import compute.ComputeFibonacci;

public class GetFibonacciTerm extends ServerResource {

	private static final Logger LOG = Logger.getLogger(GetFibonacciTerm.class);

	@Get
	public String represent() {

		List<String> ipsList = Request.getCurrent().getClientInfo().getForwardedAddresses();
		LOG.info("Size list IPs " + ipsList.size());
		
		if(!ipsList.isEmpty()) {
			String IP = ipsList.get(ipsList.size() - 1);

			LOG.info("Request from " + IP);
		}

		String indexString = (String) this.getRequestAttributes().get("index");
		int i = 0;
		try {
			i = Integer.parseInt(indexString);
		} catch (NumberFormatException e) {
			LOG.error(indexString + " is not a number");
			return "That is not a number";
		}
		LOG.info("Request to calculate Fibonacci term of order " + i);
		BigInteger r = ComputeFibonacci.computeTerm(i);

		if (r.intValue() == -1)
			return "Fibonacci term cannot be computed for negative index";

		return String.valueOf(r);
	}

}
