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

import com.zincyanide.sqlconstructor.internal.StringUtil;
import com.zincyanide.sqlconstructor.internal.Symbol;
import java.util.Objects;

/**
 *  与或条件树
 *  当某一条件不可用时，折叠或替换为代替表达式{@link Manner}
 *
 *
 *
 *
 *  {                     #root r
 *                       /   |  \
 *                      /    |   \
 *  #left[ |------ #root p   |  #right[ #root q
 *         |          |      |          /      \
 *         |       #next u   |      #left s   #right t ]
 *         |         |       |
 *        -> #next v(tail) ] |                          }
 *                           |
 *                       #next z
 *
 *
 * the conditional statement is:
 * "(p and u and v or (s or t)) and z"
 *
 *
 *
 */
public class ConditionNode
{
    protected String condition; // statement
    protected ConditionNode left; // the left option of "or"
    protected ConditionNode right; // the right option of "or"
    protected ConditionNode next; // indicates the next "and"
    protected ConditionNode tail; // the last "and" of this root

    public ConditionNode()
    {   }
    public ConditionNode(String condition)
    {
        this.condition = condition;
    }

    public ConditionNode add(String condition, boolean branch)
    {
        return add(new ConditionNode(condition), branch);
    }

    public ConditionNode add(ConditionNode n, boolean branch)
    {
        return add(this, n, branch);
    }

    public ConditionNode add(ConditionNode root, ConditionNode successor, boolean branch)
    {
        return !branch ? succeed(root, successor) : branch(root, successor);
    }

    protected ConditionNode succeed(ConditionNode root, ConditionNode successor)
    {
        ConditionNode t;
        t = ((t = root.tail) == null) ? root : t;
        t.next = successor;
        root.tail = t.next;
        return root;
    }

    protected ConditionNode branch(ConditionNode origin, ConditionNode branch)
    {
        return new NodeComponent(origin, branch);
    }

    public String ignite()
    {
        String next = this.next == null ? null : this.next.ignite();
        boolean c = !StringUtil.isEmpty(this.condition);
        boolean n = !StringUtil.isEmpty(next);

        if (!c && !n)
            return null;

        StringBuilder sb = new StringBuilder();
        if (c)
        {
            sb.append(this.condition);
            if (n)
                sb.append(Manner.AND).append(next);
        }
        else
            sb.append(next);

        return sb.toString();
    }



    public static class NodeComponent extends ConditionNode
    {
        public NodeComponent(ConditionNode left, ConditionNode right)
        {
            this.left = left;
            this.right = right;
        }

        @Override
        public ConditionNode add(String condition, boolean branch)
        {
            return this.add(new ConditionNode(condition), branch);
        }

        @Override
        public ConditionNode add(ConditionNode c, boolean branch)
        {
            if(!branch)
            {
                add(this, c, false);
                return this;
            }

            return add(this, c, true);
        }

        @Override
        public String ignite()
        {
            this.condition = implode();
            return super.ignite();
        }

        private String implode()
        {
            Objects.requireNonNull(this.left);
            Objects.requireNonNull(this.right);

            String left = this.left.ignite();
            String right = this.right.ignite();
            boolean l = !StringUtil.isEmpty(left);
            boolean r = !StringUtil.isEmpty(right);

            if (!l && !r)
                return this.condition;

            StringBuilder sb = new StringBuilder(Symbol.BRACKET_LEFT);
            if (l)
                sb.append(left);
            else
                sb.append(Manner.NONE);

            if (r)
                sb.append(Manner.OR)
                        // "OR" + "A AND B" or "A OR B" neither need to be bracketed
                        .append(right);

            sb.append(Symbol.BRACKET_RIGHT);

            return sb.toString();
        }

    }



    public interface Manner
    {
        String ALL      =   "1 = 1";
        String NONE     =   "1 = 0";
        String AND      =   " AND ";
        String OR       =   " OR ";
    }
}
