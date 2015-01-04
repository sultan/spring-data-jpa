/*
 * Copyright 2014 the original author or authors.
 *
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
 */
package org.springframework.data.jpa.domain.support;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.junit.Assume.*;
import static org.springframework.data.jpa.support.EntityManagerTestUtils.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;

/**
 * Integration tests for {@link Jsr310JpaConverters}.
 * 
 * @author Oliver Gierke
 */
public class Jsr310JpaConvertersIntegrationTests extends AbstractAttributeConverterIntegrationTests {

	@PersistenceContext EntityManager em;

	/**
	 * @see DATAJPA-650
	 */
	@Test
	public void usesJsr310JpaConverters() {

		assumeTrue(currentEntityManagerIsAJpa21EntityManager(em));

		DateTimeSample sample = new DateTimeSample();

		sample.instant = Instant.now();
		sample.localDate = LocalDate.now();
		sample.localTime = LocalTime.now();
		sample.localDateTime = LocalDateTime.now();

		em.persist(sample);
		em.clear();

		DateTimeSample result = em.find(DateTimeSample.class, sample.id);

		assertThat(result, is(notNullValue()));
		assertThat(result.instant, is(sample.instant));
		assertThat(result.localDate, is(sample.localDate));
		assertThat(result.localTime, is(sample.localTime));
		assertThat(result.localDateTime, is(sample.localDateTime));
	}
}