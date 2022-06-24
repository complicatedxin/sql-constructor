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

public class Conditions
{
    private PredicateNode root;

    public Conditions(PredicateNode root)
    {
        this.root = root;
    }

    public static Conditions first(String condition)
    {
        return new Conditions(new PredicateNode(condition));
    }

    public Conditions and(String condition)
    {
        return and(new PredicateNode(condition));
    }

    public Conditions and(PredicateNode predicate)
    {
        root = root.add(predicate, false);
        return this;
    }

    public Conditions or(String condition)
    {
        return or(new PredicateNode(condition));
    }

    public Conditions or(PredicateNode predicate)
    {
        root = root.add(predicate, true);
        return this;
    }

    public PredicateNode finish()
    {
        return root;
    }

}
