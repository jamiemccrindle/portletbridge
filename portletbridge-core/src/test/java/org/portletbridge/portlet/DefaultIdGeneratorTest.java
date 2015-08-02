package org.portletbridge.portlet;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;


/**
 * Test of {@link DefaultIdGenerator}.
 * 
 * @author <A href="mailto:argherna@gmail.com">Andy Gherna</A>
 *
 */
public class DefaultIdGeneratorTest extends TestCase {

	public DefaultIdGeneratorTest(String name) {
		super(name);
	}

	
	/**
	 * Verifies {@link DefaultIdGenerator#nextId() nextId} generates random
	 * Ids. Test passes if 10000 unique IDs are generated.
	 */
	public void testNextId() {
		IdGenerator fixture = new DefaultIdGenerator();
		
		Set<String> ids = new HashSet<String>();
		for(int i = 0; i < 10000; i++) {
			String uuid = fixture.nextId();
			if (ids.contains(uuid)) {
				fail("Id already generated");
			}
			ids.add(uuid);
		}
		
	}
}
