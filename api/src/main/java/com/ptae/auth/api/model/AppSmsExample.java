package com.ptae.auth.api.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ptae.base.model.EntityExample;

public class AppSmsExample extends EntityExample{
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AppSmsExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andUserAccountIsNull() {
            addCriterion("user_account is null");
            return (Criteria) this;
        }

        public Criteria andUserAccountIsNotNull() {
            addCriterion("user_account is not null");
            return (Criteria) this;
        }

        public Criteria andUserAccountEqualTo(String value) {
            addCriterion("user_account =", value, "userAccount");
            return (Criteria) this;
        }

        public Criteria andUserAccountNotEqualTo(String value) {
            addCriterion("user_account <>", value, "userAccount");
            return (Criteria) this;
        }

        public Criteria andUserAccountGreaterThan(String value) {
            addCriterion("user_account >", value, "userAccount");
            return (Criteria) this;
        }

        public Criteria andUserAccountGreaterThanOrEqualTo(String value) {
            addCriterion("user_account >=", value, "userAccount");
            return (Criteria) this;
        }

        public Criteria andUserAccountLessThan(String value) {
            addCriterion("user_account <", value, "userAccount");
            return (Criteria) this;
        }

        public Criteria andUserAccountLessThanOrEqualTo(String value) {
            addCriterion("user_account <=", value, "userAccount");
            return (Criteria) this;
        }

        public Criteria andUserAccountLike(String value) {
            addCriterion("user_account like", value, "userAccount");
            return (Criteria) this;
        }

        public Criteria andUserAccountNotLike(String value) {
            addCriterion("user_account not like", value, "userAccount");
            return (Criteria) this;
        }

        public Criteria andUserAccountIn(List<String> values) {
            addCriterion("user_account in", values, "userAccount");
            return (Criteria) this;
        }

        public Criteria andUserAccountNotIn(List<String> values) {
            addCriterion("user_account not in", values, "userAccount");
            return (Criteria) this;
        }

        public Criteria andUserAccountBetween(String value1, String value2) {
            addCriterion("user_account between", value1, value2, "userAccount");
            return (Criteria) this;
        }

        public Criteria andUserAccountNotBetween(String value1, String value2) {
            addCriterion("user_account not between", value1, value2, "userAccount");
            return (Criteria) this;
        }

        public Criteria andMessagesIsNull() {
            addCriterion("messages is null");
            return (Criteria) this;
        }

        public Criteria andMessagesIsNotNull() {
            addCriterion("messages is not null");
            return (Criteria) this;
        }

        public Criteria andMessagesEqualTo(String value) {
            addCriterion("messages =", value, "messages");
            return (Criteria) this;
        }

        public Criteria andMessagesNotEqualTo(String value) {
            addCriterion("messages <>", value, "messages");
            return (Criteria) this;
        }

        public Criteria andMessagesGreaterThan(String value) {
            addCriterion("messages >", value, "messages");
            return (Criteria) this;
        }

        public Criteria andMessagesGreaterThanOrEqualTo(String value) {
            addCriterion("messages >=", value, "messages");
            return (Criteria) this;
        }

        public Criteria andMessagesLessThan(String value) {
            addCriterion("messages <", value, "messages");
            return (Criteria) this;
        }

        public Criteria andMessagesLessThanOrEqualTo(String value) {
            addCriterion("messages <=", value, "messages");
            return (Criteria) this;
        }

        public Criteria andMessagesLike(String value) {
            addCriterion("messages like", value, "messages");
            return (Criteria) this;
        }

        public Criteria andMessagesNotLike(String value) {
            addCriterion("messages not like", value, "messages");
            return (Criteria) this;
        }

        public Criteria andMessagesIn(List<String> values) {
            addCriterion("messages in", values, "messages");
            return (Criteria) this;
        }

        public Criteria andMessagesNotIn(List<String> values) {
            addCriterion("messages not in", values, "messages");
            return (Criteria) this;
        }

        public Criteria andMessagesBetween(String value1, String value2) {
            addCriterion("messages between", value1, value2, "messages");
            return (Criteria) this;
        }

        public Criteria andMessagesNotBetween(String value1, String value2) {
            addCriterion("messages not between", value1, value2, "messages");
            return (Criteria) this;
        }

        public Criteria andSentTimeIsNull() {
            addCriterion("sent_time is null");
            return (Criteria) this;
        }

        public Criteria andSentTimeIsNotNull() {
            addCriterion("sent_time is not null");
            return (Criteria) this;
        }

        public Criteria andSentTimeEqualTo(Date value) {
            addCriterion("sent_time =", value, "sentTime");
            return (Criteria) this;
        }

        public Criteria andSentTimeNotEqualTo(Date value) {
            addCriterion("sent_time <>", value, "sentTime");
            return (Criteria) this;
        }

        public Criteria andSentTimeGreaterThan(Date value) {
            addCriterion("sent_time >", value, "sentTime");
            return (Criteria) this;
        }

        public Criteria andSentTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("sent_time >=", value, "sentTime");
            return (Criteria) this;
        }

        public Criteria andSentTimeLessThan(Date value) {
            addCriterion("sent_time <", value, "sentTime");
            return (Criteria) this;
        }

        public Criteria andSentTimeLessThanOrEqualTo(Date value) {
            addCriterion("sent_time <=", value, "sentTime");
            return (Criteria) this;
        }

        public Criteria andSentTimeIn(List<Date> values) {
            addCriterion("sent_time in", values, "sentTime");
            return (Criteria) this;
        }

        public Criteria andSentTimeNotIn(List<Date> values) {
            addCriterion("sent_time not in", values, "sentTime");
            return (Criteria) this;
        }

        public Criteria andSentTimeBetween(Date value1, Date value2) {
            addCriterion("sent_time between", value1, value2, "sentTime");
            return (Criteria) this;
        }

        public Criteria andSentTimeNotBetween(Date value1, Date value2) {
            addCriterion("sent_time not between", value1, value2, "sentTime");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(String value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(String value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(String value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(String value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(String value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(String value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLike(String value) {
            addCriterion("type like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotLike(String value) {
            addCriterion("type not like", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<String> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<String> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(String value1, String value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(String value1, String value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andAttribute1IsNull() {
            addCriterion("attribute1 is null");
            return (Criteria) this;
        }

        public Criteria andAttribute1IsNotNull() {
            addCriterion("attribute1 is not null");
            return (Criteria) this;
        }

        public Criteria andAttribute1EqualTo(String value) {
            addCriterion("attribute1 =", value, "attribute1");
            return (Criteria) this;
        }

        public Criteria andAttribute1NotEqualTo(String value) {
            addCriterion("attribute1 <>", value, "attribute1");
            return (Criteria) this;
        }

        public Criteria andAttribute1GreaterThan(String value) {
            addCriterion("attribute1 >", value, "attribute1");
            return (Criteria) this;
        }

        public Criteria andAttribute1GreaterThanOrEqualTo(String value) {
            addCriterion("attribute1 >=", value, "attribute1");
            return (Criteria) this;
        }

        public Criteria andAttribute1LessThan(String value) {
            addCriterion("attribute1 <", value, "attribute1");
            return (Criteria) this;
        }

        public Criteria andAttribute1LessThanOrEqualTo(String value) {
            addCriterion("attribute1 <=", value, "attribute1");
            return (Criteria) this;
        }

        public Criteria andAttribute1Like(String value) {
            addCriterion("attribute1 like", value, "attribute1");
            return (Criteria) this;
        }

        public Criteria andAttribute1NotLike(String value) {
            addCriterion("attribute1 not like", value, "attribute1");
            return (Criteria) this;
        }

        public Criteria andAttribute1In(List<String> values) {
            addCriterion("attribute1 in", values, "attribute1");
            return (Criteria) this;
        }

        public Criteria andAttribute1NotIn(List<String> values) {
            addCriterion("attribute1 not in", values, "attribute1");
            return (Criteria) this;
        }

        public Criteria andAttribute1Between(String value1, String value2) {
            addCriterion("attribute1 between", value1, value2, "attribute1");
            return (Criteria) this;
        }

        public Criteria andAttribute1NotBetween(String value1, String value2) {
            addCriterion("attribute1 not between", value1, value2, "attribute1");
            return (Criteria) this;
        }

        public Criteria andAttribute2IsNull() {
            addCriterion("attribute2 is null");
            return (Criteria) this;
        }

        public Criteria andAttribute2IsNotNull() {
            addCriterion("attribute2 is not null");
            return (Criteria) this;
        }

        public Criteria andAttribute2EqualTo(String value) {
            addCriterion("attribute2 =", value, "attribute2");
            return (Criteria) this;
        }

        public Criteria andAttribute2NotEqualTo(String value) {
            addCriterion("attribute2 <>", value, "attribute2");
            return (Criteria) this;
        }

        public Criteria andAttribute2GreaterThan(String value) {
            addCriterion("attribute2 >", value, "attribute2");
            return (Criteria) this;
        }

        public Criteria andAttribute2GreaterThanOrEqualTo(String value) {
            addCriterion("attribute2 >=", value, "attribute2");
            return (Criteria) this;
        }

        public Criteria andAttribute2LessThan(String value) {
            addCriterion("attribute2 <", value, "attribute2");
            return (Criteria) this;
        }

        public Criteria andAttribute2LessThanOrEqualTo(String value) {
            addCriterion("attribute2 <=", value, "attribute2");
            return (Criteria) this;
        }

        public Criteria andAttribute2Like(String value) {
            addCriterion("attribute2 like", value, "attribute2");
            return (Criteria) this;
        }

        public Criteria andAttribute2NotLike(String value) {
            addCriterion("attribute2 not like", value, "attribute2");
            return (Criteria) this;
        }

        public Criteria andAttribute2In(List<String> values) {
            addCriterion("attribute2 in", values, "attribute2");
            return (Criteria) this;
        }

        public Criteria andAttribute2NotIn(List<String> values) {
            addCriterion("attribute2 not in", values, "attribute2");
            return (Criteria) this;
        }

        public Criteria andAttribute2Between(String value1, String value2) {
            addCriterion("attribute2 between", value1, value2, "attribute2");
            return (Criteria) this;
        }

        public Criteria andAttribute2NotBetween(String value1, String value2) {
            addCriterion("attribute2 not between", value1, value2, "attribute2");
            return (Criteria) this;
        }

        public Criteria andAttribute3IsNull() {
            addCriterion("attribute3 is null");
            return (Criteria) this;
        }

        public Criteria andAttribute3IsNotNull() {
            addCriterion("attribute3 is not null");
            return (Criteria) this;
        }

        public Criteria andAttribute3EqualTo(String value) {
            addCriterion("attribute3 =", value, "attribute3");
            return (Criteria) this;
        }

        public Criteria andAttribute3NotEqualTo(String value) {
            addCriterion("attribute3 <>", value, "attribute3");
            return (Criteria) this;
        }

        public Criteria andAttribute3GreaterThan(String value) {
            addCriterion("attribute3 >", value, "attribute3");
            return (Criteria) this;
        }

        public Criteria andAttribute3GreaterThanOrEqualTo(String value) {
            addCriterion("attribute3 >=", value, "attribute3");
            return (Criteria) this;
        }

        public Criteria andAttribute3LessThan(String value) {
            addCriterion("attribute3 <", value, "attribute3");
            return (Criteria) this;
        }

        public Criteria andAttribute3LessThanOrEqualTo(String value) {
            addCriterion("attribute3 <=", value, "attribute3");
            return (Criteria) this;
        }

        public Criteria andAttribute3Like(String value) {
            addCriterion("attribute3 like", value, "attribute3");
            return (Criteria) this;
        }

        public Criteria andAttribute3NotLike(String value) {
            addCriterion("attribute3 not like", value, "attribute3");
            return (Criteria) this;
        }

        public Criteria andAttribute3In(List<String> values) {
            addCriterion("attribute3 in", values, "attribute3");
            return (Criteria) this;
        }

        public Criteria andAttribute3NotIn(List<String> values) {
            addCriterion("attribute3 not in", values, "attribute3");
            return (Criteria) this;
        }

        public Criteria andAttribute3Between(String value1, String value2) {
            addCriterion("attribute3 between", value1, value2, "attribute3");
            return (Criteria) this;
        }

        public Criteria andAttribute3NotBetween(String value1, String value2) {
            addCriterion("attribute3 not between", value1, value2, "attribute3");
            return (Criteria) this;
        }

        public Criteria andAttribute4IsNull() {
            addCriterion("attribute4 is null");
            return (Criteria) this;
        }

        public Criteria andAttribute4IsNotNull() {
            addCriterion("attribute4 is not null");
            return (Criteria) this;
        }

        public Criteria andAttribute4EqualTo(String value) {
            addCriterion("attribute4 =", value, "attribute4");
            return (Criteria) this;
        }

        public Criteria andAttribute4NotEqualTo(String value) {
            addCriterion("attribute4 <>", value, "attribute4");
            return (Criteria) this;
        }

        public Criteria andAttribute4GreaterThan(String value) {
            addCriterion("attribute4 >", value, "attribute4");
            return (Criteria) this;
        }

        public Criteria andAttribute4GreaterThanOrEqualTo(String value) {
            addCriterion("attribute4 >=", value, "attribute4");
            return (Criteria) this;
        }

        public Criteria andAttribute4LessThan(String value) {
            addCriterion("attribute4 <", value, "attribute4");
            return (Criteria) this;
        }

        public Criteria andAttribute4LessThanOrEqualTo(String value) {
            addCriterion("attribute4 <=", value, "attribute4");
            return (Criteria) this;
        }

        public Criteria andAttribute4Like(String value) {
            addCriterion("attribute4 like", value, "attribute4");
            return (Criteria) this;
        }

        public Criteria andAttribute4NotLike(String value) {
            addCriterion("attribute4 not like", value, "attribute4");
            return (Criteria) this;
        }

        public Criteria andAttribute4In(List<String> values) {
            addCriterion("attribute4 in", values, "attribute4");
            return (Criteria) this;
        }

        public Criteria andAttribute4NotIn(List<String> values) {
            addCriterion("attribute4 not in", values, "attribute4");
            return (Criteria) this;
        }

        public Criteria andAttribute4Between(String value1, String value2) {
            addCriterion("attribute4 between", value1, value2, "attribute4");
            return (Criteria) this;
        }

        public Criteria andAttribute4NotBetween(String value1, String value2) {
            addCriterion("attribute4 not between", value1, value2, "attribute4");
            return (Criteria) this;
        }

        public Criteria andAttribute5IsNull() {
            addCriterion("attribute5 is null");
            return (Criteria) this;
        }

        public Criteria andAttribute5IsNotNull() {
            addCriterion("attribute5 is not null");
            return (Criteria) this;
        }

        public Criteria andAttribute5EqualTo(String value) {
            addCriterion("attribute5 =", value, "attribute5");
            return (Criteria) this;
        }

        public Criteria andAttribute5NotEqualTo(String value) {
            addCriterion("attribute5 <>", value, "attribute5");
            return (Criteria) this;
        }

        public Criteria andAttribute5GreaterThan(String value) {
            addCriterion("attribute5 >", value, "attribute5");
            return (Criteria) this;
        }

        public Criteria andAttribute5GreaterThanOrEqualTo(String value) {
            addCriterion("attribute5 >=", value, "attribute5");
            return (Criteria) this;
        }

        public Criteria andAttribute5LessThan(String value) {
            addCriterion("attribute5 <", value, "attribute5");
            return (Criteria) this;
        }

        public Criteria andAttribute5LessThanOrEqualTo(String value) {
            addCriterion("attribute5 <=", value, "attribute5");
            return (Criteria) this;
        }

        public Criteria andAttribute5Like(String value) {
            addCriterion("attribute5 like", value, "attribute5");
            return (Criteria) this;
        }

        public Criteria andAttribute5NotLike(String value) {
            addCriterion("attribute5 not like", value, "attribute5");
            return (Criteria) this;
        }

        public Criteria andAttribute5In(List<String> values) {
            addCriterion("attribute5 in", values, "attribute5");
            return (Criteria) this;
        }

        public Criteria andAttribute5NotIn(List<String> values) {
            addCriterion("attribute5 not in", values, "attribute5");
            return (Criteria) this;
        }

        public Criteria andAttribute5Between(String value1, String value2) {
            addCriterion("attribute5 between", value1, value2, "attribute5");
            return (Criteria) this;
        }

        public Criteria andAttribute5NotBetween(String value1, String value2) {
            addCriterion("attribute5 not between", value1, value2, "attribute5");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}