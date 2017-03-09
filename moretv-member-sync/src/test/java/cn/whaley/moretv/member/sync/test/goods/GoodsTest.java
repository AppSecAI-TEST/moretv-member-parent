package cn.whaley.moretv.member.sync.test.goods;

import cn.whaley.moretv.member.base.constant.CacheKeyConstant;
import cn.whaley.moretv.member.model.goods.Goods;
import cn.whaley.moretv.member.sync.dto.goods.GoodsDto;
import cn.whaley.moretv.member.sync.service.goods.GoodsService;
import cn.whaley.moretv.member.sync.test.base.BaseTest;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Bob Jiang on 2017/2/20.
 */
public class GoodsTest extends BaseTest {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testGet() {
        HashOperations<String, String, String> opsHash = redisTemplate.opsForHash();
        String value = opsHash.get(CacheKeyConstant.REDIS_KEY_GOODS, "nz-goods_spu_basic-12m-001");
        GoodsDto goodsDto = JSON.parseObject(value, GoodsDto.class);
        System.out.println(goodsDto);
    }

    @Test
    public void testGetGoods() {
        Goods goods = goodsService.selectByPrimaryKey(1);
    }

    @Test
    public void testInsertGoods() {
        Goods goods = new Goods();
        goods.setGoodsName("junit test name");
        goods.setGoodsCode("junit test code");
        goods.setGoodsBaseCode("base code");
        goods.setGoodsBaseName("base name");
        goods.setGoodsSubName("sub name");
        goods.setOriginalPrice(16);
        goods.setDiscount(BigDecimal.ZERO);
        goods.setSellingPrice(10);

        Date date = new Date();
        goods.setCreateTime(date);
        goods.setUpdateTime(date);
        goodsService.insertSelective(goods);
    }
}
