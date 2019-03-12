package com.shorturl.service;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.shorturl.constants.Constants;
import com.shorturl.exception.ShortUrlException;
import com.shorturl.repository.SequenceRepo;

/**
 * SeqCounterServiceImpl class is used for creating sequence value for short
 * hash creation. It has utility method to load buffer counter value. It uses
 * AtomicLong class to manage thread safety of increment to counter.
 * 
 * @author rohit
 *
 */
@Service
public class SeqCounterServiceImpl implements SeqCounterService {

	/**
	 * AtomicLong value manages thread safe increment of sequence value.
	 */
	private AtomicLong c;
	/**
	 * endCounter maintains the buffered size which control when to query database
	 * sequence to get the next value.
	 */
	private Long endCounter;

	/**
	 * sequenceRepo provide interface for getting the next index value from
	 * sequence.
	 */
	@Autowired
	private SequenceRepo sequenceRepo;

	/**
	 * Environment files for reading application configuration
	 */
	@Autowired
	private Environment env;

	/**
	 * loadCurrentCounter method loads the current counter value from database
	 * during the initialization of
	 */
	@PostConstruct
	public void processInit() {
		getNextSequence();
		String bufferSize = StringUtils.isEmpty(env.getProperty(Constants.BUFFER_SIZE_KEY))
				? Constants.DEFAULT_BUFFER_SIZE
				: env.getProperty(Constants.BUFFER_SIZE_KEY);
		this.endCounter = c.get() + Long.parseLong(bufferSize);
	}

	/**
	 * increment method increases the value of current counter to next using
	 * AtomicLong incrementGet method. If the nextCOunter value has crossed buffered
	 * counter value then it will initialize the counter again to next value from
	 * database sequence by invoking getNextSequence method.
	 * 
	 * @return nextCounter calculated value
	 */
	public Long increment() {
		if (c != null) {
			long nextCounter = c.incrementAndGet();
			if (nextCounter > endCounter) {
				getNextSequence();
				nextCounter = c.incrementAndGet();
				return nextCounter;
			} else {
				return nextCounter;
			}
		} else {
			throw new ShortUrlException("Unable to generate next sequence, please retry", null, false, false);
		}
	}

	/**
	 * getNextSequence method is used to initialize new value from database sequence
	 * it buffers certain preset values in memory so that for every call it doesnot
	 * need to hit database.
	 * <ul>
	 * This buffer value needs to configured in two places first is while creating
	 * the sequence which is by how much value sequence needs to be incremented.
	 */
	private void getNextSequence() {
		BigInteger num = sequenceRepo.getNextSequence();
		c = new AtomicLong(num.longValue());
		this.endCounter = num.longValue() + Long.parseLong(env.getProperty(Constants.BUFFER_SIZE_KEY));
	}

	/**
	 * generateSeqeunce method creates the unique value for the long url. Value of
	 * <b>base string</b> is read from configuration file this value is used for
	 * random string generation. Logic loops till the <b>num</b> value is greater
	 * than <i>0</i> and keeps retrieving the modulus value based on the division.
	 * Based on the index, from baseString that specific index value is retrieved
	 * and value keeps appending to stringbuilder object which returns the after
	 * entire logic completes.
	 * 
	 * @param num
	 *            the incremented value based on AtomicCounter class.
	 * @return Value retrieved from above logic.
	 */
	@Override
	public String generateSeqeunce(Long num) {
		if (num != null && num > 0) {
			StringBuilder sb = new StringBuilder();
			String baseString = env.getProperty(Constants.BASE_STRING_KEY);
			// lopping indefinitely
			while (true) {
				// continues till value is greater than 0
				if (num > 0) {
					// generating modulus of the given string
					int index = (int) (num % baseString.length());
					// get the value for the given index.
					sb.append(baseString.substring(index, index + 1));
					num = num / baseString.length();
				} else {
					break;
				}
			}
			return sb.toString();
		} else {
			throw new ShortUrlException("Unable to generate next sequence, please retry", null, false, false);
		}
	}

}
