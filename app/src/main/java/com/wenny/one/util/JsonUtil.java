package com.wenny.one.util;

import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wenny.one.entity.BannerInfoEntity;
import com.wenny.one.entity.CommentEntity;
import com.wenny.one.entity.EssayEntity;
import com.wenny.one.entity.HpEntity;
import com.wenny.one.entity.MovieDetail;
import com.wenny.one.entity.MovieEntity;
import com.wenny.one.entity.MovieStoryEntity;
import com.wenny.one.entity.MusicEntity;
import com.wenny.one.entity.MusicListEntity;
import com.wenny.one.entity.QuestionEntity;
import com.wenny.one.entity.ReadBannerEntity;
import com.wenny.one.entity.ReadEntity;
import com.wenny.one.entity.SerialEntity;
import com.wenny.one.entity.SerialListEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wenny on 2016/10/23.
 */

public class JsonUtil {

    public static List<String> getIdList(String json) {
        if (json != null) {
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray data = jsonObject.getJSONArray("data");
                TypeToken<List<String>> tt = new TypeToken<List<String>>() {
                };
                return new Gson().fromJson(data.toString(), tt.getType());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static HpEntity getHpEntity(String json) {
        if (json != null) {
            return new Gson().fromJson(json, HpEntity.class);
        }
        return null;
    }

    public static List<HpEntity.DataBean> getListHp(String json){
        if (json != null){
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray data = jsonObject.getJSONArray("data");
                TypeToken<List<HpEntity.DataBean>> typeToken = new TypeToken<List<HpEntity.DataBean>>(){};
                return new Gson().fromJson(data.toString(),typeToken.getType());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static List<ReadBannerEntity> getReadBanner(String json) {
        if (json != null) {
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray data = jsonObject.getJSONArray("data");
                TypeToken<List<ReadBannerEntity>> tt = new TypeToken<List<ReadBannerEntity>>() {
                };
                return new Gson().fromJson(data.toString(), tt.getType());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static ReadEntity getReadEntity(String json) {
        if (json != null) {
            return new Gson().fromJson(json.toString(), ReadEntity.class);
        }
        return null;
    }

    public static MusicEntity getMusicEntity(String json) {
        if (json != null) {
            return new Gson().fromJson(json.toString(), MusicEntity.class);
        }
        return null;
    }

    public static List<MovieEntity> getMovieList(String json) {
        if (json != null) {
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray data = jsonObject.getJSONArray("data");
                TypeToken<List<MovieEntity>> tt = new TypeToken<List<MovieEntity>>() {
                };
                return new Gson().fromJson(data.toString(), tt.getType());
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    public static List<MovieStoryEntity> getMovieStory(String json) {
        if (json != null) {
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("data");
                TypeToken<List<MovieStoryEntity>> typeToken = new TypeToken<List<MovieStoryEntity>>() {
                };
                return new Gson().fromJson(jsonArray.toString(), typeToken.getType());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static MovieDetail getMovieDetail(String json) {
        if (json != null) {
            try {
                JSONObject jsonObject = new JSONObject(json);
                jsonObject = jsonObject.getJSONObject("data");
                TypeToken<MovieDetail> typeToken = new TypeToken<MovieDetail>() {
                };
                return new Gson().fromJson(jsonObject.toString(), typeToken.getType());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static List<CommentEntity> getComment(String json) {
        if (json != null) {
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("data");
                TypeToken<List<CommentEntity>> typeToken = new TypeToken<List<CommentEntity>>() {
                };
                return new Gson().fromJson(jsonArray.toString(), typeToken.getType());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static EssayEntity getEssay(String json) {
        if (json != null) {
            try {
                JSONObject jsonObject = new JSONObject(json);
                jsonObject = jsonObject.getJSONObject("data");
                TypeToken<EssayEntity> typeToken = new TypeToken<EssayEntity>() {
                };
                return new Gson().fromJson(jsonObject.toString(), typeToken.getType());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static SerialEntity getSerial(String json) {
        if (json != null) {
            try {
                JSONObject jsonObject = new JSONObject(json);
                jsonObject = jsonObject.getJSONObject("data");
                TypeToken<SerialEntity> typeToken = new TypeToken<SerialEntity>() {
                };
                return new Gson().fromJson(jsonObject.toString(), typeToken.getType());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static QuestionEntity getQuestion(String json) {
        if (json != null) {
            try {
                JSONObject jsonObject = new JSONObject(json);
                jsonObject = jsonObject.getJSONObject("data");
                TypeToken<QuestionEntity> typeToken = new TypeToken<QuestionEntity>() {
                };
                return new Gson().fromJson(jsonObject.toString(), typeToken.getType());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static List<List<CommentEntity>> getCommentList(List<CommentEntity> commentEntities) {
        if(commentEntities != null) {
            List<List<CommentEntity>> commentdatas = new ArrayList<>();
            List<CommentEntity> commentEntities1 = new ArrayList<>();
            for (int i = 0; i < commentEntities.size(); i++) {
                commentEntities1.add(commentEntities.get(i));
                if ((i > 0 && i % 10 == 0) || i == commentEntities.size() - 1) {
                    commentdatas.add(commentEntities1);
                    commentEntities1 = new ArrayList<>();
                }
            }
            return commentdatas;
        }
        return null;
    }

    public static SerialListEntity getSerialList(String json) {
        if (json != null) {
            return new Gson().fromJson(json.toString(), SerialListEntity.class);
        }
        return null;
    }

    public static AnimationDrawable loading(View view, int resid) {
        ImageView iv = (ImageView) view.findViewById(resid);
        AnimationDrawable animationDrawable = (AnimationDrawable) iv.getBackground();
        animationDrawable.start();
        return animationDrawable;
    }

    public static List<ReadEntity.DataBean.EssayBean> getListEssay(String json){
        if(json != null){
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray data = jsonObject.getJSONArray("data");
                TypeToken<List<ReadEntity.DataBean.EssayBean>> typeToken = new TypeToken<List<ReadEntity.DataBean.EssayBean>>(){};
                return new Gson().fromJson(data.toString(),typeToken.getType());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    public static List<ReadEntity.DataBean.QuestionBean> getListQuestion(String json){
        if(json != null){
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray data = jsonObject.getJSONArray("data");
                TypeToken<List<ReadEntity.DataBean.QuestionBean>> typeToken = new TypeToken<List<ReadEntity.DataBean.QuestionBean>>(){};
                return new Gson().fromJson(data.toString(),typeToken.getType());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static List<ReadEntity.DataBean.SerialBean> getListSerial(String json){
        if(json != null){
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray data = jsonObject.getJSONArray("data");
                TypeToken<List<ReadEntity.DataBean.SerialBean>> typeToken = new TypeToken<List<ReadEntity.DataBean.SerialBean>>(){};
                return new Gson().fromJson(data.toString(),typeToken.getType());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static List<MusicListEntity> getListMusic(String json){
        if(json != null){
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray data = jsonObject.getJSONArray("data");
                TypeToken<List<MusicListEntity>> typeToken = new TypeToken<List<MusicListEntity>>(){};
                return new Gson().fromJson(data.toString(),typeToken.getType());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    public static List<BannerInfoEntity> getBannerInfo(String json){
        if(json != null){
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray data = jsonObject.getJSONArray("data");
                TypeToken<List<BannerInfoEntity>> typeToken = new TypeToken<List<BannerInfoEntity>>(){};
                return new Gson().fromJson(data.toString(),typeToken.getType());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
