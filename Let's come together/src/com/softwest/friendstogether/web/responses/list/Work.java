package com.softwest.friendstogether.web.responses.list;

import java.util.ArrayList;
import java.util.List;

import com.softwest.friendstogether.web.responses.list.include.Employer;
import com.softwest.friendstogether.web.responses.list.include.Location;
import com.softwest.friendstogether.web.responses.list.include.Position;
import com.softwest.friendstogether.web.responses.list.include.list.Projects;

public class Work

{
  public Employer employer;
  public Location location;
  public Position position;
  
  public Object start_date;
  
  public Object end_date;
  
  public List<Projects> projects = new ArrayList<Projects>(); 
}
