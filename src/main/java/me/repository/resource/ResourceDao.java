/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package me.repository.resource;

import java.util.List;

import me.entity.Doc;
import me.entity.Resource;


public interface ResourceDao  {

	List<Resource> findPanels();

	List<Resource> findNodes(String  id);

}
