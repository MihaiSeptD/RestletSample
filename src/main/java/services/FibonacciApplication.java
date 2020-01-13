package services;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

public class FibonacciApplication extends Application {

	private static final Logger LOG = LogManager.getLogger(FibonacciApplication.class);
	
	public synchronized Restlet createInboundRoot() {
		
		LOG.info("start create inbount for Fibonacci calculation");
		
		Router router = new Router(getContext());

		router.attach("/fibonacci/{index}", GetFibonacciTerm.class);

		return router;
	}
}
