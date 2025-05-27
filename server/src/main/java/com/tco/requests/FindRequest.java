package com.tco.requests;

import java.util.List;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

import com.tco.misc.sqlGuide;

public class FindRequest extends Request {

    private static final transient Logger log = LoggerFactory.getLogger(FindRequest.class);

    private String match;
    private Long limit;

    private Long found;
    private ArrayList<HashMap<String,String>> places;

    @Override
    public void buildResponse() {
        found = sqlGuide.getFound(match);
        places = getPlacesFromDatabase();

        log.trace("buildResponse -> {}", this);
    }

    public ArrayList<HashMap<String,String>> getPlacesFromDatabase()
    {
        return sqlGuide.getPlaces(match, (limit==0) ? found : limit);
    }

  /* The following methods exist only for testing purposes and are not used
  during normal execution, including the constructor. */

  public FindRequest()
  {
    requestType = "find";
    match = "";
    limit = 10L;
  }

  public FindRequest(String match, Long limit)
  {
    requestType = "find";
    this.match = match;
    this.limit = limit;
  }

  public String getMatch()
  {
    return match;
  }

  public Long getLimit()
  {
    return limit;
  }

  public Long getFound()
  {
    return found;
  }

  public ArrayList<HashMap<String,String>> getPlaces()
  {
    return places;
  }
}
