package com.softwest.friendstogether.web.responses.list;

import java.util.ArrayList;
import java.util.List;

import com.softwest.friendstogether.web.responses.PeopleNearMe;
import com.softwest.friendstogether.web.responses.wrappers.ResponseBody;

public class ListPeople
  extends ResponseBody
{
 public List<PeopleNearMe> result = new ArrayList<PeopleNearMe>();
}
