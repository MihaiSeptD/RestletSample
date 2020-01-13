package compute;

import java.math.BigInteger;
import java.util.SortedMap;
import java.util.concurrent.ConcurrentSkipListMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ComputeFibonacci {

	private static SortedMap<Integer, BigInteger> hashValues = new ConcurrentSkipListMap<>();

	private static final Logger LOG = LogManager.getLogger(ComputeFibonacci.class);

	private static final int RANGE_OF_CACHE_USAGE = 30;

	private static final int MAX_HASH_SIZE = 1000;
	
	public static BigInteger computeTerm(int index) {

		if (index < 0)
			return BigInteger.ONE.negate();

		if (index == 0)
			return BigInteger.ZERO;
		if (index == 1)
			return BigInteger.ONE;

		if (hashValues.containsKey(index)) {
			LOG.info("Return from cache");
			return hashValues.get(index);
		}

		Integer lastKey = 1;
		if (!hashValues.isEmpty()) {
			lastKey = hashValues.lastKey();
			LOG.info("Last key " + lastKey);
		}

		BigInteger current = null;
		BigInteger previous = null;
		if (lastKey > RANGE_OF_CACHE_USAGE && index - lastKey <= RANGE_OF_CACHE_USAGE) {
			LOG.info("HIT");
			previous = hashValues.get(lastKey - 1);
			current = hashValues.get(lastKey);
			for (int i = lastKey + 1; i <= index; i++) {
				BigInteger new_current = previous.add(current);
				previous = current;
				current = new_current;
			}
		} else {
			previous = BigInteger.ZERO;
			current = BigInteger.ONE;
			for (int i = 2; i <= index; i++) {
				BigInteger new_current = previous.add(current);
				previous = current;
				current = new_current;
			}
		}
		hashValues.put(index - 1, previous);
		hashValues.put(index, current);

		if(hashValues.size() > MAX_HASH_SIZE)
			hashValues = new ConcurrentSkipListMap<>();
		
		return current;
	}
}
