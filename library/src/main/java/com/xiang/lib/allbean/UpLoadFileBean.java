package com.xiang.lib.allbean;

import java.util.List;

/**
 * author : fengzhangwei
 * date : 2019/11/14
 */
public class UpLoadFileBean {

    /**
     * msg : 成功
     * code : 1
     * data : [{"url":"http://172.16.200.235:8081/upload/images/20191115160150.jpg"},{"url":"http://172.16.200.235:8081/upload/images/20191115160150.jpg"},{"url":"http://172.16.200.235:8081/upload/images/20191115160150.jpg"}]
     */

    private String msg;
    private int code;
    private List<DataBean> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * url : http://172.16.200.235:8081/upload/images/20191115160150.jpg
         */

        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
