/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package me.repository.docShared;

import java.util.List;

import me.entity.Doc;


public interface DocSharedDao  {

	List<Doc> findDocs();
}
