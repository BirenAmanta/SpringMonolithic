package com.mindtree.phoneBook.utility;

import org.modelmapper.ModelMapper;

public class Conversion<T,E> {

	public E getConvertedObject(T source,Class<E> destination)
	{
		return new ModelMapper().map(source,destination);
	}

}
