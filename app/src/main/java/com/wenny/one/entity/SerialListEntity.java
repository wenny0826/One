package com.wenny.one.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 连载专辑
 */

public class SerialListEntity implements Serializable{

    /**
     * res : 0
     * data : {"id":"33","title":"玩家","finished":"0","list":[{"id":"181","serial_id":"33","number":"1"},{"id":"182","serial_id":"33","number":"2"},{"id":"183","serial_id":"33","number":"3"},{"id":"184","serial_id":"33","number":"4"},{"id":"185","serial_id":"33","number":"5"},{"id":"186","serial_id":"33","number":"6"},{"id":"187","serial_id":"33","number":"7"},{"id":"188","serial_id":"33","number":"8"},{"id":"189","serial_id":"33","number":"9"},{"id":"190","serial_id":"33","number":"10"},{"id":"191","serial_id":"33","number":"11"},{"id":"192","serial_id":"33","number":"12"},{"id":"193","serial_id":"33","number":"13"},{"id":"194","serial_id":"33","number":"14"}]}
     */

    private int res;
    /**
     * id : 33
     * title : 玩家
     * finished : 0
     * list : [{"id":"181","serial_id":"33","number":"1"},{"id":"182","serial_id":"33","number":"2"},{"id":"183","serial_id":"33","number":"3"},{"id":"184","serial_id":"33","number":"4"},{"id":"185","serial_id":"33","number":"5"},{"id":"186","serial_id":"33","number":"6"},{"id":"187","serial_id":"33","number":"7"},{"id":"188","serial_id":"33","number":"8"},{"id":"189","serial_id":"33","number":"9"},{"id":"190","serial_id":"33","number":"10"},{"id":"191","serial_id":"33","number":"11"},{"id":"192","serial_id":"33","number":"12"},{"id":"193","serial_id":"33","number":"13"},{"id":"194","serial_id":"33","number":"14"}]
     */

    private DataBean data;

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        private String id;
        private String title;
        private String finished;
        /**
         * id : 181
         * serial_id : 33
         * number : 1
         */

        private List<ListBean> list;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getFinished() {
            return finished;
        }

        public void setFinished(String finished) {
            this.finished = finished;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean implements Serializable{
            private String id;
            private String serial_id;
            private String number;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getSerial_id() {
                return serial_id;
            }

            public void setSerial_id(String serial_id) {
                this.serial_id = serial_id;
            }

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }
        }
    }
}
