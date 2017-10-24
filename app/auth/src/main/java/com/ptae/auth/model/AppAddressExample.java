package com.ptae.auth.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ptae.core.model.EntityExample;

public class AppAddressExample extends EntityExample{
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public AppAddressExample() {
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

        public Criteria andHomeAddressIsNull() {
            addCriterion("home_address is null");
            return (Criteria) this;
        }

        public Criteria andHomeAddressIsNotNull() {
            addCriterion("home_address is not null");
            return (Criteria) this;
        }

        public Criteria andHomeAddressEqualTo(String value) {
            addCriterion("home_address =", value, "homeAddress");
            return (Criteria) this;
        }

        public Criteria andHomeAddressNotEqualTo(String value) {
            addCriterion("home_address <>", value, "homeAddress");
            return (Criteria) this;
        }

        public Criteria andHomeAddressGreaterThan(String value) {
            addCriterion("home_address >", value, "homeAddress");
            return (Criteria) this;
        }

        public Criteria andHomeAddressGreaterThanOrEqualTo(String value) {
            addCriterion("home_address >=", value, "homeAddress");
            return (Criteria) this;
        }

        public Criteria andHomeAddressLessThan(String value) {
            addCriterion("home_address <", value, "homeAddress");
            return (Criteria) this;
        }

        public Criteria andHomeAddressLessThanOrEqualTo(String value) {
            addCriterion("home_address <=", value, "homeAddress");
            return (Criteria) this;
        }

        public Criteria andHomeAddressLike(String value) {
            addCriterion("home_address like", value, "homeAddress");
            return (Criteria) this;
        }

        public Criteria andHomeAddressNotLike(String value) {
            addCriterion("home_address not like", value, "homeAddress");
            return (Criteria) this;
        }

        public Criteria andHomeAddressIn(List<String> values) {
            addCriterion("home_address in", values, "homeAddress");
            return (Criteria) this;
        }

        public Criteria andHomeAddressNotIn(List<String> values) {
            addCriterion("home_address not in", values, "homeAddress");
            return (Criteria) this;
        }

        public Criteria andHomeAddressBetween(String value1, String value2) {
            addCriterion("home_address between", value1, value2, "homeAddress");
            return (Criteria) this;
        }

        public Criteria andHomeAddressNotBetween(String value1, String value2) {
            addCriterion("home_address not between", value1, value2, "homeAddress");
            return (Criteria) this;
        }

        public Criteria andCompnayAddressIsNull() {
            addCriterion("compnay_address is null");
            return (Criteria) this;
        }

        public Criteria andCompnayAddressIsNotNull() {
            addCriterion("compnay_address is not null");
            return (Criteria) this;
        }

        public Criteria andCompnayAddressEqualTo(String value) {
            addCriterion("compnay_address =", value, "compnayAddress");
            return (Criteria) this;
        }

        public Criteria andCompnayAddressNotEqualTo(String value) {
            addCriterion("compnay_address <>", value, "compnayAddress");
            return (Criteria) this;
        }

        public Criteria andCompnayAddressGreaterThan(String value) {
            addCriterion("compnay_address >", value, "compnayAddress");
            return (Criteria) this;
        }

        public Criteria andCompnayAddressGreaterThanOrEqualTo(String value) {
            addCriterion("compnay_address >=", value, "compnayAddress");
            return (Criteria) this;
        }

        public Criteria andCompnayAddressLessThan(String value) {
            addCriterion("compnay_address <", value, "compnayAddress");
            return (Criteria) this;
        }

        public Criteria andCompnayAddressLessThanOrEqualTo(String value) {
            addCriterion("compnay_address <=", value, "compnayAddress");
            return (Criteria) this;
        }

        public Criteria andCompnayAddressLike(String value) {
            addCriterion("compnay_address like", value, "compnayAddress");
            return (Criteria) this;
        }

        public Criteria andCompnayAddressNotLike(String value) {
            addCriterion("compnay_address not like", value, "compnayAddress");
            return (Criteria) this;
        }

        public Criteria andCompnayAddressIn(List<String> values) {
            addCriterion("compnay_address in", values, "compnayAddress");
            return (Criteria) this;
        }

        public Criteria andCompnayAddressNotIn(List<String> values) {
            addCriterion("compnay_address not in", values, "compnayAddress");
            return (Criteria) this;
        }

        public Criteria andCompnayAddressBetween(String value1, String value2) {
            addCriterion("compnay_address between", value1, value2, "compnayAddress");
            return (Criteria) this;
        }

        public Criteria andCompnayAddressNotBetween(String value1, String value2) {
            addCriterion("compnay_address not between", value1, value2, "compnayAddress");
            return (Criteria) this;
        }

        public Criteria andAddTimeIsNull() {
            addCriterion("add_time is null");
            return (Criteria) this;
        }

        public Criteria andAddTimeIsNotNull() {
            addCriterion("add_time is not null");
            return (Criteria) this;
        }

        public Criteria andAddTimeEqualTo(Date value) {
            addCriterion("add_time =", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeNotEqualTo(Date value) {
            addCriterion("add_time <>", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeGreaterThan(Date value) {
            addCriterion("add_time >", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("add_time >=", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeLessThan(Date value) {
            addCriterion("add_time <", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeLessThanOrEqualTo(Date value) {
            addCriterion("add_time <=", value, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeIn(List<Date> values) {
            addCriterion("add_time in", values, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeNotIn(List<Date> values) {
            addCriterion("add_time not in", values, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeBetween(Date value1, Date value2) {
            addCriterion("add_time between", value1, value2, "addTime");
            return (Criteria) this;
        }

        public Criteria andAddTimeNotBetween(Date value1, Date value2) {
            addCriterion("add_time not between", value1, value2, "addTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andHomeLongitudeIsNull() {
            addCriterion("home_longitude is null");
            return (Criteria) this;
        }

        public Criteria andHomeLongitudeIsNotNull() {
            addCriterion("home_longitude is not null");
            return (Criteria) this;
        }

        public Criteria andHomeLongitudeEqualTo(String value) {
            addCriterion("home_longitude =", value, "homeLongitude");
            return (Criteria) this;
        }

        public Criteria andHomeLongitudeNotEqualTo(String value) {
            addCriterion("home_longitude <>", value, "homeLongitude");
            return (Criteria) this;
        }

        public Criteria andHomeLongitudeGreaterThan(String value) {
            addCriterion("home_longitude >", value, "homeLongitude");
            return (Criteria) this;
        }

        public Criteria andHomeLongitudeGreaterThanOrEqualTo(String value) {
            addCriterion("home_longitude >=", value, "homeLongitude");
            return (Criteria) this;
        }

        public Criteria andHomeLongitudeLessThan(String value) {
            addCriterion("home_longitude <", value, "homeLongitude");
            return (Criteria) this;
        }

        public Criteria andHomeLongitudeLessThanOrEqualTo(String value) {
            addCriterion("home_longitude <=", value, "homeLongitude");
            return (Criteria) this;
        }

        public Criteria andHomeLongitudeLike(String value) {
            addCriterion("home_longitude like", value, "homeLongitude");
            return (Criteria) this;
        }

        public Criteria andHomeLongitudeNotLike(String value) {
            addCriterion("home_longitude not like", value, "homeLongitude");
            return (Criteria) this;
        }

        public Criteria andHomeLongitudeIn(List<String> values) {
            addCriterion("home_longitude in", values, "homeLongitude");
            return (Criteria) this;
        }

        public Criteria andHomeLongitudeNotIn(List<String> values) {
            addCriterion("home_longitude not in", values, "homeLongitude");
            return (Criteria) this;
        }

        public Criteria andHomeLongitudeBetween(String value1, String value2) {
            addCriterion("home_longitude between", value1, value2, "homeLongitude");
            return (Criteria) this;
        }

        public Criteria andHomeLongitudeNotBetween(String value1, String value2) {
            addCriterion("home_longitude not between", value1, value2, "homeLongitude");
            return (Criteria) this;
        }

        public Criteria andHomeLatitudeIsNull() {
            addCriterion("home_latitude is null");
            return (Criteria) this;
        }

        public Criteria andHomeLatitudeIsNotNull() {
            addCriterion("home_latitude is not null");
            return (Criteria) this;
        }

        public Criteria andHomeLatitudeEqualTo(String value) {
            addCriterion("home_latitude =", value, "homeLatitude");
            return (Criteria) this;
        }

        public Criteria andHomeLatitudeNotEqualTo(String value) {
            addCriterion("home_latitude <>", value, "homeLatitude");
            return (Criteria) this;
        }

        public Criteria andHomeLatitudeGreaterThan(String value) {
            addCriterion("home_latitude >", value, "homeLatitude");
            return (Criteria) this;
        }

        public Criteria andHomeLatitudeGreaterThanOrEqualTo(String value) {
            addCriterion("home_latitude >=", value, "homeLatitude");
            return (Criteria) this;
        }

        public Criteria andHomeLatitudeLessThan(String value) {
            addCriterion("home_latitude <", value, "homeLatitude");
            return (Criteria) this;
        }

        public Criteria andHomeLatitudeLessThanOrEqualTo(String value) {
            addCriterion("home_latitude <=", value, "homeLatitude");
            return (Criteria) this;
        }

        public Criteria andHomeLatitudeLike(String value) {
            addCriterion("home_latitude like", value, "homeLatitude");
            return (Criteria) this;
        }

        public Criteria andHomeLatitudeNotLike(String value) {
            addCriterion("home_latitude not like", value, "homeLatitude");
            return (Criteria) this;
        }

        public Criteria andHomeLatitudeIn(List<String> values) {
            addCriterion("home_latitude in", values, "homeLatitude");
            return (Criteria) this;
        }

        public Criteria andHomeLatitudeNotIn(List<String> values) {
            addCriterion("home_latitude not in", values, "homeLatitude");
            return (Criteria) this;
        }

        public Criteria andHomeLatitudeBetween(String value1, String value2) {
            addCriterion("home_latitude between", value1, value2, "homeLatitude");
            return (Criteria) this;
        }

        public Criteria andHomeLatitudeNotBetween(String value1, String value2) {
            addCriterion("home_latitude not between", value1, value2, "homeLatitude");
            return (Criteria) this;
        }

        public Criteria andCompanyLongitudeIsNull() {
            addCriterion("company_longitude is null");
            return (Criteria) this;
        }

        public Criteria andCompanyLongitudeIsNotNull() {
            addCriterion("company_longitude is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyLongitudeEqualTo(String value) {
            addCriterion("company_longitude =", value, "companyLongitude");
            return (Criteria) this;
        }

        public Criteria andCompanyLongitudeNotEqualTo(String value) {
            addCriterion("company_longitude <>", value, "companyLongitude");
            return (Criteria) this;
        }

        public Criteria andCompanyLongitudeGreaterThan(String value) {
            addCriterion("company_longitude >", value, "companyLongitude");
            return (Criteria) this;
        }

        public Criteria andCompanyLongitudeGreaterThanOrEqualTo(String value) {
            addCriterion("company_longitude >=", value, "companyLongitude");
            return (Criteria) this;
        }

        public Criteria andCompanyLongitudeLessThan(String value) {
            addCriterion("company_longitude <", value, "companyLongitude");
            return (Criteria) this;
        }

        public Criteria andCompanyLongitudeLessThanOrEqualTo(String value) {
            addCriterion("company_longitude <=", value, "companyLongitude");
            return (Criteria) this;
        }

        public Criteria andCompanyLongitudeLike(String value) {
            addCriterion("company_longitude like", value, "companyLongitude");
            return (Criteria) this;
        }

        public Criteria andCompanyLongitudeNotLike(String value) {
            addCriterion("company_longitude not like", value, "companyLongitude");
            return (Criteria) this;
        }

        public Criteria andCompanyLongitudeIn(List<String> values) {
            addCriterion("company_longitude in", values, "companyLongitude");
            return (Criteria) this;
        }

        public Criteria andCompanyLongitudeNotIn(List<String> values) {
            addCriterion("company_longitude not in", values, "companyLongitude");
            return (Criteria) this;
        }

        public Criteria andCompanyLongitudeBetween(String value1, String value2) {
            addCriterion("company_longitude between", value1, value2, "companyLongitude");
            return (Criteria) this;
        }

        public Criteria andCompanyLongitudeNotBetween(String value1, String value2) {
            addCriterion("company_longitude not between", value1, value2, "companyLongitude");
            return (Criteria) this;
        }

        public Criteria andCompanyLatitudeIsNull() {
            addCriterion("company_latitude is null");
            return (Criteria) this;
        }

        public Criteria andCompanyLatitudeIsNotNull() {
            addCriterion("company_latitude is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyLatitudeEqualTo(String value) {
            addCriterion("company_latitude =", value, "companyLatitude");
            return (Criteria) this;
        }

        public Criteria andCompanyLatitudeNotEqualTo(String value) {
            addCriterion("company_latitude <>", value, "companyLatitude");
            return (Criteria) this;
        }

        public Criteria andCompanyLatitudeGreaterThan(String value) {
            addCriterion("company_latitude >", value, "companyLatitude");
            return (Criteria) this;
        }

        public Criteria andCompanyLatitudeGreaterThanOrEqualTo(String value) {
            addCriterion("company_latitude >=", value, "companyLatitude");
            return (Criteria) this;
        }

        public Criteria andCompanyLatitudeLessThan(String value) {
            addCriterion("company_latitude <", value, "companyLatitude");
            return (Criteria) this;
        }

        public Criteria andCompanyLatitudeLessThanOrEqualTo(String value) {
            addCriterion("company_latitude <=", value, "companyLatitude");
            return (Criteria) this;
        }

        public Criteria andCompanyLatitudeLike(String value) {
            addCriterion("company_latitude like", value, "companyLatitude");
            return (Criteria) this;
        }

        public Criteria andCompanyLatitudeNotLike(String value) {
            addCriterion("company_latitude not like", value, "companyLatitude");
            return (Criteria) this;
        }

        public Criteria andCompanyLatitudeIn(List<String> values) {
            addCriterion("company_latitude in", values, "companyLatitude");
            return (Criteria) this;
        }

        public Criteria andCompanyLatitudeNotIn(List<String> values) {
            addCriterion("company_latitude not in", values, "companyLatitude");
            return (Criteria) this;
        }

        public Criteria andCompanyLatitudeBetween(String value1, String value2) {
            addCriterion("company_latitude between", value1, value2, "companyLatitude");
            return (Criteria) this;
        }

        public Criteria andCompanyLatitudeNotBetween(String value1, String value2) {
            addCriterion("company_latitude not between", value1, value2, "companyLatitude");
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