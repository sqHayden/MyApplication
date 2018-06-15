package com.demo.gank.json;

import java.util.List;

/**
 * Created by hayden on 18-6-12.
 */

public class Data {
    private boolean error;
    private List<Result> results;

    public boolean isError() {
        return error;
    }

    public List<Result> getResults() {
        return results;
    }
}
