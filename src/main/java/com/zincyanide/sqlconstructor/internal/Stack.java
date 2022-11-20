/*
 * Copyright 2022 Zincyanide
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zincyanide.sqlconstructor.internal;

import java.util.ArrayList;

/**
 *  A THREAD-UNSAFE stack
 * @deprecated never use
 */
@Deprecated
public class Stack<E>
{
    private ArrayList<E> container = new ArrayList<>();

    private int top = -1;

    public void push(E e)
    {
        if(++top < container.size())
            container.set(top, e);
        container.add(top, e);
    }

    public E pop()
    {
        if(top > -1)
            return container.get(top--);
        return null;
    }

    public boolean isEmpty()
    {
        return top < 0;
    }


}
