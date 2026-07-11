-- Tio-Flix catalog foundation

create table if not exists public.categories (
    id bigint generated always as identity primary key,
    slug text not null unique,
    name text not null,
    sort_order integer not null default 0,
    is_active boolean not null default true,
    created_at timestamptz not null default timezone('utc', now())
);

create table if not exists public.movies (
    id uuid primary key default gen_random_uuid(),
    title text not null,
    description text,
    poster_url text,
    backdrop_url text,
    trailer_url text,
    stream_key text,
    release_year integer,
    duration_minutes integer,
    maturity_rating text,
    language text,
    is_featured boolean not null default false,
    is_published boolean not null default false,
    published_at timestamptz,
    created_at timestamptz not null default timezone('utc', now()),
    updated_at timestamptz not null default timezone('utc', now())
);

create table if not exists public.movie_categories (
    movie_id uuid not null references public.movies(id) on delete cascade,
    category_id bigint not null references public.categories(id) on delete cascade,
    sort_order integer not null default 0,
    primary key (movie_id, category_id)
);

create index if not exists movies_published_idx
on public.movies (is_published, published_at desc);

create index if not exists movies_featured_idx
on public.movies (is_featured, is_published);

create index if not exists categories_active_sort_idx
on public.categories (is_active, sort_order, name);

alter table public.categories enable row level security;
alter table public.movies enable row level security;
alter table public.movie_categories enable row level security;

drop policy if exists "Authenticated users can read active categories" on public.categories;
create policy "Authenticated users can read active categories"
on public.categories
for select
to authenticated
using (is_active = true);

drop policy if exists "Authenticated users can read published movies" on public.movies;
create policy "Authenticated users can read published movies"
on public.movies
for select
to authenticated
using (is_published = true);

drop policy if exists "Authenticated users can read published movie categories" on public.movie_categories;
create policy "Authenticated users can read published movie categories"
on public.movie_categories
for select
to authenticated
using (
    exists (
        select 1
        from public.movies
        where movies.id = movie_categories.movie_id
          and movies.is_published = true
    )
    and exists (
        select 1
        from public.categories
        where categories.id = movie_categories.category_id
          and categories.is_active = true
    )
);

grant usage on schema public to authenticated;
grant select on public.categories to authenticated;
grant select on public.movies to authenticated;
grant select on public.movie_categories to authenticated;

insert into public.categories (slug, name, sort_order)
values
    ('trending', 'Trending Now', 10),
    ('new-releases', 'New Releases', 20),
    ('action', 'Action', 30),
    ('drama', 'Drama', 40)
on conflict (slug) do update set
    name = excluded.name,
    sort_order = excluded.sort_order,
    is_active = true;
