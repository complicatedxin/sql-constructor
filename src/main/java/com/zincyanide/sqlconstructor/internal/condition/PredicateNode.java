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

public class PredicateNode
{
    protected String condition;
    protected PredicateNode left;
    protected PredicateNode right;
    protected PredicateNode next;
    protected PredicateNode tail;

    public PredicateNode()
    {   }
    public PredicateNode(String condition)
    {
        this.condition = condition;
    }

    public PredicateNode add(String condition, boolean branch)
    {
        return add(new PredicateNode(condition), branch);
    }

    public PredicateNode add(PredicateNode n, boolean branch)
    {
        return add(this, n, branch);
    }

    public PredicateNode add(PredicateNode root, PredicateNode successor, boolean branch)
    {
        return !branch ? succeed(root, successor) : branch(root, successor);
    }

    protected PredicateNode succeed(PredicateNode root, PredicateNode successor)
    {
        PredicateNode t;
        t = ((t = root.tail) == null) ? root : t;
        t.next = successor;
        root.tail = t.next;
        return root;
    }

    protected PredicateNode branch(PredicateNode origin, PredicateNode branch)
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



    public static class NodeComponent extends PredicateNode
    {
        public NodeComponent(PredicateNode left, PredicateNode right)
        {
            this.left = left;
            this.right = right;
        }

        @Override
        public PredicateNode add(String condition, boolean branch)
        {
            return this.add(new PredicateNode(condition), branch);
        }

        @Override
        public PredicateNode add(PredicateNode c, boolean branch)
        {
            if(!branch)
            {
                this.right = add(this.right, c, false);
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
//                        .append(Symbol.BRACKET_LEFT)
                        .append(right);
//                        .append(Symbol.BRACKET_RIGHT); // OR + "A AND B" or "A OR B" neither need Bracket
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
