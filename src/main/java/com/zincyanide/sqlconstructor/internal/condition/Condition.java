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

package com.zincyanide.sqlconstructor.internal.condition;

/**
 *  用于管理条件树
 *  以及为Conditional子类提供接口
 */
public class Condition
{
    private PredicateNode root;

    public Condition()
    {
        this.root = new PredicateNode();
    }

    public void and(String condition)
    {
        root = root.add(condition, false);
    }

    public void and(PredicateNode predicate)
    {
        root = root.add(predicate, false);
    }

    public void or(String condition)
    {
        root = root.add(condition, true);
    }

    public void or(PredicateNode predicate)
    {
        root = root.add(predicate, true);
    }

    public String getConditions()
    {
        return root.ignite();
    }

}
