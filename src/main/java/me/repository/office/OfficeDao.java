/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package me.repository.office;

import me.entity.Office;
import me.repository.common.Page;


public interface OfficeDao  {


	Page<Office> findOffices(Page<Office> pageObj, Office office);

}
