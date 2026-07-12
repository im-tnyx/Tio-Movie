-- User-owned favorites / My List.

create table if not exists public.favorites (
    user_id uuid not null references auth.users(id) on delete cascade,
    content_id uuid not null references public.content(id) on delete cascade,
    created_at timestamptz not null default timezone('utc', now()),
    primary key (user_id, content_id)
);

create index if not exists favorites_user_recent_idx
on public.favorites (user_id, created_at desc);

alter table public.favorites enable row level security;

drop policy if exists "Users can read own favorites" on public.favorites;
create policy "Users can read own favorites"
on public.favorites
for select
to authenticated
using ((select auth.uid()) = user_id);

drop policy if exists "Users can insert own favorites" on public.favorites;
create policy "Users can insert own favorites"
on public.favorites
for insert
to authenticated
with check ((select auth.uid()) = user_id);

drop policy if exists "Users can delete own favorites" on public.favorites;
create policy "Users can delete own favorites"
on public.favorites
for delete
to authenticated
using ((select auth.uid()) = user_id);

grant select, insert, delete on public.favorites to authenticated;

comment on table public.favorites is
'User-owned My List entries for published movies and series.';
