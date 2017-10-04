package com.rustedbrain.study.course;

import org.junit.*;

import java.util.BitSet;

public class BitSetTest {

    private static BitSet bitSet1;
    private static BitSet bitSet2;
    private static int setBitsCount;

    @BeforeClass
    public static void setUpBeforeClass() {
        bitSet1 = new BitSet();
        bitSet2 = new BitSet();
        setBitsCount = 10;
    }

    @AfterClass
    public static void tearDownAfterClass() {
        bitSet1 = null;
        bitSet2 = null;
    }

    @Before
    public void setUpBeforeMethod() {
        bitSet1.set(0, true);
        bitSet1.set(1, true);
        bitSet1.set(2, true);
        bitSet1.set(3, false);
        bitSet1.set(4, false);
        bitSet1.set(5, false);
        bitSet1.set(6, false);
        bitSet1.set(7, false);
        bitSet1.set(8, false);
        bitSet1.set(9, false);

        bitSet2.set(0, true);
        bitSet2.set(1, true);
        bitSet2.set(2, false);
        bitSet2.set(3, false);
        bitSet2.set(4, false);
        bitSet2.set(5, false);
        bitSet2.set(6, false);
        bitSet2.set(7, false);
        bitSet2.set(8, false);
        bitSet2.set(9, false);
    }

    @Test
    public void cardinalityBitSet() {
        Assert.assertEquals(3, bitSet1.cardinality());
        Assert.assertEquals(2, bitSet2.cardinality());
    }

    @Test
    public void orBitSet() {
        bitSet1.or(bitSet2);
        for (int i = 0; i < setBitsCount; i++) {
            Assert.assertEquals(true, bitSet1.get(i));
        }
    }

    @Test
    public void andBitSet() {
        bitSet1.and(bitSet2);
        for (int i = 0; i < setBitsCount; i++) {
            Assert.assertEquals(false, bitSet1.get(i));
        }
    }

    @Test
    public void flipBitSet() {
        for (int i = 0; i < setBitsCount; i++) {
            if (i % 2 == 0) {
                bitSet1.flip(i);
            } else {
                bitSet2.flip(i);
            }
        }
        for (int i = 0; i < setBitsCount; i++) {
            if (i % 2 == 0) {
                Assert.assertEquals(false, bitSet1.get(i));
            } else {
                Assert.assertEquals(false, bitSet2.get(i));
            }
        }
    }

    @Test
    public void clearBitSet() {
        bitSet1.clear();
        bitSet2.clear();
        Assert.assertEquals(0, bitSet1.length());
        Assert.assertEquals(0, bitSet2.length());
    }

    @Test
    public void andNotBitSet() {
        bitSet1.andNot(bitSet2);
        for (int i = 0; i < setBitsCount; i++) {
            if (i % 2 == 0) {
                Assert.assertEquals(true, bitSet1.get(i));
            } else {
                Assert.assertEquals(true, bitSet2.get(i));
            }
        }
    }

    @Test
    public void nextSetBit() {
        Assert.assertEquals(0, bitSet1.nextSetBit(0));
    }

    @Test
    public void nextClearBit() {
        Assert.assertEquals(1, bitSet1.nextClearBit(0));
    }

    @Test
    public void previousSetBit() {
        Assert.assertEquals(0, bitSet1.previousSetBit(0));
    }

    @Test
    public void previousClearBit() {
        Assert.assertEquals(-1, bitSet1.previousClearBit(0));
    }

    @Test
    public void xorNotBitSet() {
        bitSet1.xor(bitSet2);
        for (int i = 0; i < setBitsCount; i++) {
            Assert.assertEquals(true, bitSet1.get(i));
        }
    }

    @After
    public void tearDownAfterMethod() {
        bitSet1.clear();
        bitSet2.clear();
    }
}
