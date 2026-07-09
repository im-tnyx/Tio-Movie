# Database Schema

This document defines the recommended Supabase PostgreSQL schema for Tio-Flix.

## Tables

Recommended tables:

```text
profiles
categories
movies
movie_categories
watch_history
favorites
movie_ad_breaks
ad_events
```

## Profiles

```sql
create table public.profiles (
  id uuid primary key references auth.users(id) on delete cascade,
  email text,
  full_name text,
  avatar_url text,
  provider text default 'email',
  created_at timestamptz default now(),
  updated_at timestamptz default now()
);
```

## Categories

```sql
create table public.categories (
  id uuid primary key default gen_random_uuid(),
  name text not null unique,
  slug text not null unique,
  created_at timestamptz default now()
);
```

## Movies

```sql
create table public.movies (
  id uuid primary key default gen_random_uuid(),
  title text not null,
  description text,
  poster_url text,
  banner_url text,
  video_url text not null,
  trailer_url text,
  duration_seconds integer,
  release_year integer,
  age_rating text,
  language text,
  is_featured boolean default false,
  is_active boolean default true,
  created_at timestamptz default now(),
  updated_at timestamptz default now()
);
```

## Movie categories

```sql
create table public.movie_categories (
  movie_id uuid references public.movies(id) on delete cascade,
  category_id uuid references public.categories(id) on delete cascade,
  primary key(movie_id, category_id)
);
```

## Watch history

```sql
create table public.watch_history (
  id uuid primary key default gen_random_uuid(),
  user_id uuid references auth.users(id) on delete cascade,
  movie_id uuid references public.movies(id) on delete cascade,
  progress_seconds integer default 0,
  duration_seconds integer,
  completed boolean default false,
  updated_at timestamptz default now(),
  unique(user_id, movie_id)
);
```

## Favorites

```sql
create table public.favorites (
  id uuid primary key default gen_random_uuid(),
  user_id uuid references auth.users(id) on delete cascade,
  movie_id uuid references public.movies(id) on delete cascade,
  created_at timestamptz default now(),
  unique(user_id, movie_id)
);
```

## Movie ad breaks

```sql
create table public.movie_ad_breaks (
  id uuid primary key default gen_random_uuid(),
  movie_id uuid references public.movies(id) on delete cascade,
  break_seconds integer not null,
  ad_tag_url text,
  is_active boolean default true,
  created_at timestamptz default now()
);
```

## Ad events

```sql
create table public.ad_events (
  id uuid primary key default gen_random_uuid(),
  user_id uuid references auth.users(id) on delete set null,
  movie_id uuid references public.movies(id) on delete cascade,
  event_type text not null,
  break_seconds integer,
  ad_tag_url text,
  created_at timestamptz default now()
);
```

## Enable RLS

```sql
alter table public.profiles enable row level security;
alter table public.watch_history enable row level security;
alter table public.favorites enable row level security;
alter table public.ad_events enable row level security;
```

## Basic RLS policies

```sql
create policy "Users can read own profile"
on public.profiles
for select
using (auth.uid() = id);

create policy "Users can update own profile"
on public.profiles
for update
using (auth.uid() = id);

create policy "Users can manage own watch history"
on public.watch_history
for all
using (auth.uid() = user_id)
with check (auth.uid() = user_id);

create policy "Users can manage own favorites"
on public.favorites
for all
using (auth.uid() = user_id)
with check (auth.uid() = user_id);

create policy "Users can insert own ad events"
on public.ad_events
for insert
with check (auth.uid() = user_id);
```

## Public read policies

Movies and categories can be public read if the app is free and ad-supported.

```sql
create policy "Anyone can read active movies"
on public.movies
for select
using (is_active = true);

create policy "Anyone can read categories"
on public.categories
for select
using (true);
```

## Important note

Admin insert/update/delete actions should not be done directly from the Android app. Use Supabase dashboard, a secure backend, or Edge Functions.
