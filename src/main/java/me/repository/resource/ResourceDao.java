/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package me.repository.resource;

import java.util.List;

import me.entity.Doc;
import me.entity.Menu;


public interface ResourceDao  {

	List<Menu> findPanels(Long userId);

	List<Menu> findNodes(String  id);

}
