package cn.com.talklaw.model;

import com.jusfoun.baselibrary.base.BaseModel;

import java.io.Serializable;

/**
 * @author zhaoyapeng
 * @version create time:18/1/2317:22
 * @Email zyp@jusfoun.com
 * @Description ${ 产品Item model}
 */
public class ProductItemModel extends BaseModel implements Serializable{

    //              "id": "2",
//              "lawyer": "0", //律师姓名
//              "img": 1,//封面
//              "title": "234234234",//标题
//              "createtime": "2017-12-24 06:21" //时间
//              "comment_num": "234234234",//评论数
//              "like_num": "234234234",//赞数
    public String id;
    public String lawyer;
    public String img;
    public String title;
    public String createtime;
    public String comment_num;
    public String like_num;
    public String price;


}

