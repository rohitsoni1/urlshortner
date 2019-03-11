package com.shorturl.service;
/**
 * SeqCounterServiceImpl class is used for creating sequence value for short
 * hash creation. It has utility method to load buffer counter value. It uses
 * AtomicLong class to manage thread safety of increment to counter.
 * 
 * @author rohit
 *
 */
public interface SeqCounterService {
	/**
	 * increment method increases the value of current counter to next using
	 * AtomicLong incrementGet method. If the nextCOunter value has crossed buffered
	 * counter value then it will initialize the counter again to next value from
	 * database sequence by invoking getNextSequence method.
	 * <b>Check implementation class for details</b>
	 * @return nextCounter calculated value
	 */
	Long increment();
	/**
	 * generateSeqeunce method creates the unique value for the long url. Value of
	 * <b>base string</b> is read from configuration file this value is used for
	 * random string generation. Logic loops till the <b>num</b> value is greater
	 * than <i>0</i> and keeps retrieving the modulus value based on the division.
	 * Based on the index, from baseString that specific index value is retrieved
	 * and value keeps appending to stringbuilder object which returns the after
	 * entire logic completes.
	 * <b>Check implementation class for details</b>
	 * @param num
	 *            the incremented value based on AtomicCounter class.
	 * @return Value retrieved from above logic.
	 */
	String generateSeqeunce(Long num);

}
