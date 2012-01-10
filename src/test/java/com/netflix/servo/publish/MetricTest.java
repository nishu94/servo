/*
 * #%L
 * servo
 * %%
 * Copyright (C) 2011 - 2012 Netflix
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package com.netflix.servo.publish;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class MetricTest {

    private final Map<String,String> tags1 =
        ImmutableMap.of("cluster", "foo", "asg", "foo-v000");

    private final Map<String,String> tags2 =
        ImmutableMap.of("cluster", "foo", "asg", "foo-v001");

    @Test(expectedExceptions = NullPointerException.class)
    public void testNullName() throws Exception {
        long now = System.currentTimeMillis();
        new Metric(null, tags1, now, 42);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testNullTags() throws Exception {
        long now = System.currentTimeMillis();
        new Metric("a", null, now, 42);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testNullValue() throws Exception {
        long now = System.currentTimeMillis();
        new Metric("a", tags1, now, null);
    }

    @Test
    public void testAccessors() throws Exception {
        long now = System.currentTimeMillis();
        Metric m1 = new Metric("a", tags1, now, 42);
        assertEquals(m1.name(), "a");
        assertEquals(m1.tags(), tags1);
        assertEquals(m1.timestamp(), now);
        assertEquals(m1.value(), 42);
    }

    @Test
    public void testEquals() throws Exception {
        long now = System.currentTimeMillis();
        Metric m1 = new Metric("a", tags1, now, 42);
        Metric m2 = new Metric("a", tags2, now, 42);
        Metric m3 = new Metric("a", tags1, now, 42);

        assertFalse(m1.equals(null));
        assertFalse(m1.equals(m2.toString()));
        assertTrue(m1.equals(m1));
        assertFalse(m1.equals(m2));
        assertTrue(m1.equals(m3));
    }

    @Test
    public void testHashCode() throws Exception {
        long now = System.currentTimeMillis();
        Metric m1 = new Metric("a", tags1, now, 42);
        Metric m2 = new Metric("a", tags2, now, 42);
        Metric m3 = new Metric("a", tags1, now, 42);

        assertTrue(m1.hashCode() == m1.hashCode());
        assertTrue(m1.hashCode() != m2.hashCode());
        assertTrue(m1.hashCode() == m3.hashCode());
    }
}
