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

public class StringUtil
{
    public static boolean isEmpty(String str)
    {
        return str == null || str.isEmpty();
    }

    public static void requireNonEmpty(String str)
    {
        requireNonEmpty(str, "a string is null or empty");
    }

    public static void requireNonEmpty(String str, String errorMsg)
    {
        if(isEmpty(str))
            throw new IllegalArgumentException(errorMsg);
    }

    public static boolean isWhite(String str)
    {
        return str == null || str.trim().isEmpty();
    }

    public static void requireNonWhite(String str)
    {
        requireNonWhite(str, "a string is empty or filled with whitespace");
    }

    public static void requireNonWhite(String str, String errorMsg)
    {
        if(isWhite(str))
            throw new IllegalArgumentException(errorMsg);
    }
}
