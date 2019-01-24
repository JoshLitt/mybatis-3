/**
 *    Copyright 2009-2019 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.submitted.foreach_pair;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.ibatis.BaseDataTest;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ForEachPairTest {

  private static SqlSessionFactory sqlSessionFactory;

  @BeforeAll
  static void setUp() throws Exception {
    // create a SqlSessionFactory
    try (Reader reader = Resources.getResourceAsReader("org/apache/ibatis/submitted/foreach_pair/mybatis-config.xml")) {
      sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
    }

    // populate in-memory database
    BaseDataTest.runScript(sqlSessionFactory.getConfiguration().getEnvironment().getDataSource(),
            "org/apache/ibatis/submitted/foreach/CreateDB.sql");
  }

  @Test
  void shouldCountByCriteriaRangesBad() {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      Mapper mapper = sqlSession.getMapper(Mapper.class);

      Criteria criteria = new Criteria();
      List<Pair<Integer, Integer>> testRanges = Collections.unmodifiableList(Arrays.asList(Pair.of(1,2), Pair.of(4,10)));
      criteria.setRanges(testRanges);
      int count = mapper.countByCriteriaRangeBad(criteria);
      Assertions.assertEquals(5, count);
    }
  }

  @Test
  void shouldCountByIdListBad() {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      Mapper mapper = sqlSession.getMapper(Mapper.class);

      List<Pair<Integer, Integer>> testRanges = Collections.unmodifiableList(Arrays.asList(Pair.of(1,2), Pair.of(4,10)));
      int count = mapper.countByRangeListBad(testRanges);
      Assertions.assertEquals(5, count);
    }
  }

  @Test
  void shouldCountByCriteriaRangesGood() {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      Mapper mapper = sqlSession.getMapper(Mapper.class);

      Criteria criteria = new Criteria();
      List<Pair<Integer, Integer>> testRanges = Collections.unmodifiableList(Arrays.asList(Pair.of(1,2), Pair.of(4,10)));
      criteria.setRanges(testRanges);
      int count = mapper.countByCriteriaRangeGood(criteria);
      Assertions.assertEquals(5, count);
    }
  }

  @Test
  void shouldCountByIdListGood() {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      Mapper mapper = sqlSession.getMapper(Mapper.class);

      List<Pair<Integer, Integer>> testRanges = Collections.unmodifiableList(Arrays.asList(Pair.of(1,2), Pair.of(4,10)));
      int count = mapper.countByRangeListGood(testRanges);
      Assertions.assertEquals(5, count);
    }
  }
}
