/*
 * Java Genetic Algorithm Library (@!identifier!@).
 * Copyright (c) @!year!@ Franz Wilhelmstötter
 *  
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.	See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * Author:
 * 	 Franz Wilhelmstötter (franz.wilhelmstoetter@gmx.at)
 * 	 
 */
package org.jenetics.util;

import static java.lang.Math.min;
import static org.jenetics.util.Validator.nonNull;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

/** 
 * Array class which wraps the the java build in array type T[]. Once the array
 * is created the array length can't be changed (like the build in array). 
 * <strong>This array is not synchronized.</strong> If multiple threads access
 * an {@code Array} concurrently, and at least one of the threads modifies the
 * array, it <strong>must</strong> be synchronized externally.
 * <br/>
 * Use the {@link #asList()} method to work together with the 
 * <a href="http://download.oracle.com/javase/6/docs/technotes/guides/collections/index.html">
 * Java Collection Framework</a>.
 * 
 * @param <T> the element type of the array.
 * 
 * @author <a href="mailto:franz.wilhelmstoetter@gmx.at">Franz Wilhelmstötter</a>
 * @version $Id$
 */
public final class Array<T> 
	extends ArraySeq<T> 
	implements 
		MSeq<T>, 
		RandomAccess
{
	private static final long serialVersionUID = 2L;
			
	@SuppressWarnings("rawtypes")
	private static final Array EMPTY = new Array(0);
	
	/**
	 * Return the empty array.
	 * 
	 * @param <T> the element type.
	 * @return empty array.
	 */
	@SuppressWarnings("unchecked")
	public static <T> Array<T> empty() {
		return (Array<T>)EMPTY;
	}	
	
	Array(final ArrayRef array, final int start, final int end) {
		super(array, start, end);
	}
	
	/**
	 * Create a new array with the given length.
	 * 
	 * @param length the array length.
	  * @throws NegativeArraySizeException if the specified {@code length} 
	  *			is negative
	 */
	public Array(final int length) {
		super(length);
	}
	
	/**
	 * Create a new array with length one. The array will be initialized with 
	 * the given value.
	 * 
	 * @param first the only element of the array.
	 */
	public Array(final T first) {
		this(1);
		_array.data[0] = first;
	}
	
	/**
	 * Create a new array with length two. The array will be initialized with
	 * the given values.
	 * 
	 * @param first first array element.
	 * @param second second array element.
	 */
	public Array(
		final T first, 
		final T second
	) {
		this(2);
		_array.data[0] = first;
		_array.data[1] = second;
	}
	
	/**
	 * Create a new array with length three. The array will be initialized with
	 * the given values.
	 * 
	 * @param first first array element.
	 * @param second second array element.
	 * @param third third array element.
	 */
	public Array(
		final T first, 
		final T second, 
		final T third
	) {
		this(3);
		_array.data[0] = first;
		_array.data[1] = second;
		_array.data[2] = third;
	}
	
	/**
	 * Create a new array with length four. The array will be initialized with
	 * the given values.
	 * 
	 * @param first first array element.
	 * @param second second array element.
	 * @param third third array element.
	 * @param fourth fourth array element.
	 */
	public Array(
		final T first, 
		final T second, 
		final T third, 
		final T fourth
	) {
		this(4);
		_array.data[0] = first;
		_array.data[1] = second;
		_array.data[2] = third;
		_array.data[3] = fourth;
	}
	
	/**
	 * Create a new array with length five. The array will be initialized with
	 * the given values.
	 * 
	 * @param first first array element.
	 * @param second second array element.
	 * @param third third array element.
	 * @param fourth fourth array element.
	 * @param fifth fifth array element.
	 */
	public Array(
		final T first, 
		final T second, 
		final T third, 
		final T fourth,
		final T fifth
	) {
		this(5);
		_array.data[0] = first;
		_array.data[1] = second;
		_array.data[2] = third;
		_array.data[3] = fourth;
		_array.data[4] = fifth;
	}
	
	/**
	 * Create a new array from the given values.
	 * 
	 * @param first first array element.
	 * @param second second array element.
	 * @param third third array element.
	 * @param fourth fourth array element.
	 * @param fifth fifth array element.
	 * @param rest the rest of the array element.
	 * @throws NullPointerException if the {@code rest} array is {@code null}.
	 */
	public Array(
		final T first,
		final T second,
		final T third,
		final T fourth,
		final T fifth,
		final T... rest
	) {
		this(5 + rest.length);
		_array.data[0] = first;
		_array.data[1] = second;
		_array.data[2] = third;
		_array.data[3] = fourth;
		_array.data[4] = fifth;
		System.arraycopy(rest, 0, _array.data, 5, rest.length);
	}
	
	/**
	 * Create a new array from the given values.
	 * 
	 * @param values the array values.
	 * @throws NullPointerException if the {@code values} array is {@code null}.
	 */
	public Array(final T[] values) {
		this(values.length);
		System.arraycopy(values, 0, _array.data, 0, values.length);
	}
	
	/**
	 * Create a new Array from the values of the given Collection. The order of
	 * the elements are determined by the iterator of the Collection.
	 * 
	 * @param values the array values.
	 * @throws NullPointerException if the {@code values} array is {@code null}.
	 */
	public Array(final Collection<? extends T> values) {
		this(values.size());
		
		int index = 0;
		for (Iterator<? extends T> it = values.iterator(); it.hasNext(); ++index) {
			_array.data[index] = it.next();
		}
	}
	
	/**
	 * Selects all elements of this list which satisfy a predicate. 
	 * 
	 * @param predicate the predicate used to test elements.
	 * @return a new array consisting of all elements of this list that satisfy 
	 *         the given {@code predicate}. The order of the elements is 
	 *         preserved.
	 * @throws NullPointerException if the given {@code predicate} is 
	 *         {@code null}. 
	 */
	public Array<T> filter(final Predicate<? super T> predicate) {
		final Array<T> copy = new Array<T>(length());
		
		int index = 0;
		for (int i = 0, n = length(); i < n; ++i) {
			final T value = get(i);
			if (predicate.evaluate(value)) {
				copy.set(index++, value);
			}
		}
		
		return copy.subSeq(0, index);
	}
	
	@Override
	public void set(final int index, final T value) {
		checkIndex(index);
		
		_array.cloneIfSealed();
		_array.data[index + _start] = value;
	}
	
	@Override
	public Array<T> fill(final T value) {
		_array.cloneIfSealed();
		for (int i = _start; i < _end; ++i) {
			_array.data[i] = value;
		}
		return this;
	}
	
	@Override
	public Array<T> fill(final Iterator<? extends T> it) {
		_array.cloneIfSealed();
		for (int i = _start; i < _end && it.hasNext(); ++i) {
			_array.data[i] = it.next();
		}
		return this;
	}
	
	@Override
	public Array<T> fill(final T[] values) {
		_array.cloneIfSealed();
		System.arraycopy(values, 0, _array.data, _start, min(length(), values.length));
		return this;
	}
	
	@Override
	public Array<T> fill(final Factory<? extends T> factory) {
		nonNull(factory);
		
		_array.cloneIfSealed();
		for (int i = _start; i < _end; ++i) {
			_array.data[i] = factory.newInstance();
		}
		return this;
	}
	
	@Override
	public ISeq<T> toISeq() {
		_array._sealed = true;
		return new ArrayISeq<T>(new ArrayRef(_array.data), _start, _end);
	}
	
	/**
	 * Create a new array which contains the values of {@code this} and the
	 * given {@code value}. The length of the new array is 
	 * {@code this.length() + 1}. The returned array is not sealed.
	 * 
	 * @param value the value to append to this array.
	 * @return a new array which contains the values of {@code this} and the
	 * 		  given {@code value}
	 */
	public Array<T> append(final T value) {
		final Array<T> array = new Array<T>(length() + 1);
		System.arraycopy(_array.data, _start, array._array.data, 0, length());
		array._array.data[array.length() - 1] = value;
		return array;
	}
	
	/**
	 * Create a new array which contains the values of {@code this} and the
	 * given {@code array}. The length of the new array is 
	 * {@code this.length() + array.length()}. The returned array is not sealed.
	 * 
	 * @param array the array to append to this array.
	 * @return a new array which contains the values of {@code this} and the
	 * 		  given {@code array}
	 * @throws NullPointerException if the {@code arrays} is {@code null}.
	 */
	public Array<T> append(final Array<? extends T> array) {
		final Array<T> appended = new Array<T>(length() + array.length());
		
		System.arraycopy(
				_array.data, _start, 
				appended._array.data, 0, length()
			);
		System.arraycopy(
				array._array.data, array._start, 
				appended._array.data, length(), array.length()
			);
		
		return appended;
	}
	
	/**
	 * Create a new array which contains the values of {@code this} and the
	 * given {@code values}. The length of the new array is 
	 * {@code this.length() + values.size()}. The returned array is not sealed.
	 * 
	 * @param values the array to append to this array.
	 * @return a new array which contains the values of {@code this} and the
	 * 		  given {@code array}
	 * @throws NullPointerException if the {@code values} is {@code null}.
	 */
	public Array<T> append(final Collection<? extends T> values) {
		nonNull(values, "Values");
		final Array<T> array = new Array<T>(length() + values.size());
		
		System.arraycopy(_array.data, _start, array._array.data, 0, length());
		int index = length();
		for (Iterator<? extends T> it = values.iterator(); it.hasNext(); ++index) {
			array._array.data[index] = it.next();
		}
		
		return array;
	}
	
	/**
	 * Create a new array with element type {@code B}.
	 * 
	 * @param <B> the element type of the new array.
	 * @param converter the array element converter.
	 * @return a new array with element type {@code B}.
	 * @throws NullPointerException if the element {@code converter} is 
	 *         {@code null}.
	 */
	public <B> Array<B> map(final Converter<? super T, ? extends B> converter) {
		nonNull(converter, "Converter");
		
		final int length = length();
		final Array<B> result = new Array<B>(length);
		assert (result._array.data.length == length);
		
		for (int i = length; --i <= 0;) {
			@SuppressWarnings("unchecked")
			final T value = (T)_array.data[i + _start];
			result._array.data[i] = converter.convert(value);
		}
		return result;
	}
	
	@Override
	public Array<T> copy() {
		return new Array<T>(new ArrayRef(toArray()), 0, length());
	}

	@Override
	public Array<T> subSeq(final int start, final int end) {
		checkIndex(start, end);
		return new Array<T>(_array, start + _start, end + _start);
	}
		
	@Override
	public Array<T> subSeq(final int start) {
		return subSeq(start, length());
	}
	
	@Override
	public List<T> asList() {
		return new ArrayMSeqList<T>(this);
	}
	
	@Override
	public ListIterator<T> listIterator() {
		return new ArrayMSeqIterator<T>(this);
	}
	
}
