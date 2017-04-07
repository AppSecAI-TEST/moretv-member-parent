package cn.whaley.moretv.member.base.service.impl;


import cn.whaley.moretv.member.base.mapper.GenericMapper;
import cn.whaley.moretv.member.base.model.BaseModel;
import cn.whaley.moretv.member.base.service.GenericService;

import java.io.Serializable;
import java.util.List;

/**
 * GenericServiceImpl
 *
 * Created by Bob Jiang on 2017/2/13.
 */
public abstract class GenericServiceImpl<T extends BaseModel<PK>, PK extends Serializable, M extends GenericMapper<T, PK>>
        implements GenericService<T, PK> {

    public abstract M getGenericMapper();

    public int deleteByPrimaryKey(PK id) {
        return getGenericMapper().deleteByPrimaryKey(id);
    }

    public int insert(T model) {
        return getGenericMapper().insert(model);
    }

    public int insertSelective(T model) {
        return getGenericMapper().insertSelective(model);
    }

    public T selectByPrimaryKey(PK id) {
        return getGenericMapper().selectByPrimaryKey(id);
    }

    public List<T> selectAll() {
        return getGenericMapper().selectAll();
    }

    public int updateByPrimaryKey(T model) {
        return getGenericMapper().updateByPrimaryKey(model);
    }

    public int updateByPrimaryKeySelective(T model) {
        return getGenericMapper().updateByPrimaryKeySelective(model);
    }
}
