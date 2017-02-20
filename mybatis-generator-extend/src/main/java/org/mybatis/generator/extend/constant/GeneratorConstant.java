package org.mybatis.generator.extend.constant;

/**
 * Created by Bob Jiang on 2017/2/13.
 */
public interface GeneratorConstant {

    String BASE_MODEL_CLASS_PATH = "cn.whaley.moretv.member.base.model.BaseModel";

    String GENERIC_MAPPER_CLASS_PATH = "cn.whaley.moretv.member.base.mapper.GenericMapper";

    String GENERIC_SERVICE_CLASS_PATH = "cn.whaley.moretv.member.base.service.GenericService";

    String GENERIC_SERVICE_IMPL_CLASS_PATH = "cn.whaley.moretv.member.base.service.impl.GenericServiceImpl";

    String AUTOWIRED_CLASS_PATH = "org.springframework.beans.factory.annotation.Autowired";

    String SERVICE_CLASS_PATH = "org.springframework.stereotype.Service";

    String TRANSACTIONAL_CLASS_PATH = "org.springframework.transaction.annotation.Transactional";



}
