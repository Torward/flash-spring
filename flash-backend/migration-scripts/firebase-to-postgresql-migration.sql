-- Firebase to PostgreSQL Migration Script
-- This script provides templates for migrating data from Firebase to PostgreSQL
-- Execute these queries in order for each service

-- =============================================================================
-- BALANCE SERVICE MIGRATION
-- =============================================================================

-- Create balances table if not exists
CREATE TABLE IF NOT EXISTS balances (
    user_id VARCHAR(255) PRIMARY KEY,
    balance DECIMAL(15,2) NOT NULL DEFAULT 0.00,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert balance data from Firebase (replace with actual Firebase export)
-- INSERT INTO balances (user_id, balance, created_at, updated_at)
-- SELECT user_id, balance, created_at, updated_at
-- FROM firebase_balance_export;

-- =============================================================================
-- TOKENS SERVICE MIGRATION
-- =============================================================================

-- Create tokens table if not exists
CREATE TABLE IF NOT EXISTS tokens (
    user_id VARCHAR(255) PRIMARY KEY,
    fcm_token TEXT NOT NULL,
    device_type VARCHAR(50),
    device_id VARCHAR(255),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_active BOOLEAN NOT NULL DEFAULT TRUE
);

-- Insert token data from Firebase
-- INSERT INTO tokens (user_id, fcm_token, device_type, device_id, created_at, updated_at, is_active)
-- SELECT user_id, fcm_token, device_type, device_id, created_at, updated_at, is_active
-- FROM firebase_tokens_export;

-- =============================================================================
-- USERS SERVICE MIGRATION
-- =============================================================================

-- Create users table with Firebase-compatible fields
CREATE TABLE IF NOT EXISTS users (
    user_id VARCHAR(255) PRIMARY KEY,
    username VARCHAR(255) UNIQUE,
    email VARCHAR(255) UNIQUE,
    phone VARCHAR(20),
    display_name VARCHAR(255),
    bio TEXT,
    profile_picture_url TEXT,
    cover_picture_url TEXT,
    is_verified BOOLEAN DEFAULT FALSE,
    is_private BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_login TIMESTAMP,
    is_online BOOLEAN DEFAULT FALSE
);

-- Create user_counts table
CREATE TABLE IF NOT EXISTS user_counts (
    user_id VARCHAR(255) PRIMARY KEY,
    followers_count BIGINT DEFAULT 0,
    following_count BIGINT DEFAULT 0,
    posts_count BIGINT DEFAULT 0,
    likes_count BIGINT DEFAULT 0,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create blocked_users table
CREATE TABLE IF NOT EXISTS blocked_users (
    id SERIAL PRIMARY KEY,
    blocker_id VARCHAR(255) NOT NULL,
    blocked_id VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(blocker_id, blocked_id)
);

-- Create notifications table
CREATE TABLE IF NOT EXISTS notifications (
    id SERIAL PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL,
    type VARCHAR(50) NOT NULL,
    title VARCHAR(255),
    message TEXT,
    data JSONB,
    is_read BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create user_highlights table
CREATE TABLE IF NOT EXISTS user_highlights (
    id SERIAL PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    cover_image_url TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =============================================================================
-- POSTS SERVICE MIGRATION
-- =============================================================================

-- Create posts table
CREATE TABLE IF NOT EXISTS posts (
    post_id VARCHAR(255) PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL,
    content TEXT,
    media_urls TEXT[],
    media_type VARCHAR(50),
    location VARCHAR(255),
    is_reel BOOLEAN DEFAULT FALSE,
    is_story BOOLEAN DEFAULT FALSE,
    expires_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create post_likes table
CREATE TABLE IF NOT EXISTS post_likes (
    id SERIAL PRIMARY KEY,
    post_id VARCHAR(255) NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    reaction_type VARCHAR(50) DEFAULT 'like',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(post_id, user_id)
);

-- Create post_comments table
CREATE TABLE IF NOT EXISTS post_comments (
    comment_id VARCHAR(255) PRIMARY KEY,
    post_id VARCHAR(255) NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    parent_comment_id VARCHAR(255),
    content TEXT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create post_extra table
CREATE TABLE IF NOT EXISTS post_extra (
    post_id VARCHAR(255) PRIMARY KEY,
    views_count BIGINT DEFAULT 0,
    shares_count BIGINT DEFAULT 0,
    saves_count BIGINT DEFAULT 0,
    comments_count BIGINT DEFAULT 0,
    likes_count BIGINT DEFAULT 0,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =============================================================================
-- STORIES SERVICE MIGRATION
-- =============================================================================

-- Create stories table
CREATE TABLE IF NOT EXISTS stories (
    story_id VARCHAR(255) PRIMARY KEY,
    user_id VARCHAR(255) NOT NULL,
    content TEXT,
    media_url TEXT,
    media_type VARCHAR(50),
    expires_at TIMESTAMP NOT NULL,
    is_highlight BOOLEAN DEFAULT FALSE,
    highlight_id BIGINT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create story_views table
CREATE TABLE IF NOT EXISTS story_views (
    id SERIAL PRIMARY KEY,
    story_id VARCHAR(255) NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    viewed_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(story_id, user_id)
);

-- =============================================================================
-- FOLLOWS SERVICE MIGRATION
-- =============================================================================

-- Create follows table
CREATE TABLE IF NOT EXISTS follows (
    id SERIAL PRIMARY KEY,
    follower_id VARCHAR(255) NOT NULL,
    following_id VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(follower_id, following_id)
);

-- =============================================================================
-- SAVES SERVICE MIGRATION
-- =============================================================================

-- Create saves table
CREATE TABLE IF NOT EXISTS saves (
    id SERIAL PRIMARY KEY,
    post_id VARCHAR(255) NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(post_id, user_id)
);

-- =============================================================================
-- REACTIONS SERVICE MIGRATION
-- =============================================================================

-- Create reactions table
CREATE TABLE IF NOT EXISTS reactions (
    id SERIAL PRIMARY KEY,
    post_id VARCHAR(255) NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    reaction_type VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(post_id, user_id)
);

-- =============================================================================
-- REPORTS SERVICE MIGRATION
-- =============================================================================

-- Create reports table
CREATE TABLE IF NOT EXISTS reports (
    id SERIAL PRIMARY KEY,
    reporter_id VARCHAR(255) NOT NULL,
    reported_id VARCHAR(255),
    reported_post_id VARCHAR(255),
    reported_comment_id VARCHAR(255),
    report_type VARCHAR(50) NOT NULL,
    reason TEXT,
    status VARCHAR(50) DEFAULT 'pending',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    resolved_at TIMESTAMP,
    resolved_by VARCHAR(255)
);

-- =============================================================================
-- GROUPS SERVICE MIGRATION
-- =============================================================================

-- Create groups table
CREATE TABLE IF NOT EXISTS groups (
    group_id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    cover_image_url TEXT,
    owner_id VARCHAR(255) NOT NULL,
    is_private BOOLEAN DEFAULT FALSE,
    member_count BIGINT DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create group_members table
CREATE TABLE IF NOT EXISTS group_members (
    id SERIAL PRIMARY KEY,
    group_id VARCHAR(255) NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    role VARCHAR(50) DEFAULT 'member',
    joined_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(group_id, user_id)
);

-- =============================================================================
-- INDEXES FOR PERFORMANCE
-- =============================================================================

-- Create indexes for better query performance
CREATE INDEX IF NOT EXISTS idx_posts_user_id ON posts(user_id);
CREATE INDEX IF NOT EXISTS idx_posts_created_at ON posts(created_at DESC);
CREATE INDEX IF NOT EXISTS idx_post_likes_post_id ON post_likes(post_id);
CREATE INDEX IF NOT EXISTS idx_post_comments_post_id ON post_comments(post_id);
CREATE INDEX IF NOT EXISTS idx_post_comments_parent ON post_comments(parent_comment_id);
CREATE INDEX IF NOT EXISTS idx_follows_follower ON follows(follower_id);
CREATE INDEX IF NOT EXISTS idx_follows_following ON follows(following_id);
CREATE INDEX IF NOT EXISTS idx_stories_user_id ON stories(user_id);
CREATE INDEX IF NOT EXISTS idx_stories_expires ON stories(expires_at);
CREATE INDEX IF NOT EXISTS idx_notifications_user_id ON notifications(user_id);
CREATE INDEX IF NOT EXISTS idx_notifications_created_at ON notifications(created_at DESC);
CREATE INDEX IF NOT EXISTS idx_tokens_user_id ON tokens(user_id);
CREATE INDEX IF NOT EXISTS idx_tokens_active ON tokens(is_active);

-- =============================================================================
-- DATA VALIDATION QUERIES
-- =============================================================================

-- Count records in each table after migration
SELECT 'users' as table_name, COUNT(*) as count FROM users
UNION ALL
SELECT 'posts', COUNT(*) FROM posts
UNION ALL
SELECT 'post_likes', COUNT(*) FROM post_likes
UNION ALL
SELECT 'post_comments', COUNT(*) FROM post_comments
UNION ALL
SELECT 'follows', COUNT(*) FROM follows
UNION ALL
SELECT 'stories', COUNT(*) FROM stories
UNION ALL
SELECT 'tokens', COUNT(*) FROM tokens
UNION ALL
SELECT 'balances', COUNT(*) FROM balances;

-- Check for data integrity
SELECT 'Orphaned post likes' as issue,
       COUNT(*) as count
FROM post_likes pl
LEFT JOIN posts p ON pl.post_id = p.post_id
WHERE p.post_id IS NULL

UNION ALL

SELECT 'Orphaned comments' as issue,
       COUNT(*) as count
FROM post_comments pc
LEFT JOIN posts p ON pc.post_id = p.post_id
WHERE p.post_id IS NULL

UNION ALL

SELECT 'Orphaned follows' as issue,
       COUNT(*) as count
FROM follows f
LEFT JOIN users u1 ON f.follower_id = u1.user_id
LEFT JOIN users u2 ON f.following_id = u2.user_id
WHERE u1.user_id IS NULL OR u2.user_id IS NULL;

-- =============================================================================
-- MIGRATION LOGGING
-- =============================================================================

-- Create migration log table
CREATE TABLE IF NOT EXISTS migration_log (
    id SERIAL PRIMARY KEY,
    service_name VARCHAR(100) NOT NULL,
    table_name VARCHAR(100) NOT NULL,
    records_migrated BIGINT NOT NULL,
    migration_start TIMESTAMP NOT NULL,
    migration_end TIMESTAMP NOT NULL,
    status VARCHAR(50) NOT NULL,
    error_message TEXT
);

-- Insert migration completion log
-- INSERT INTO migration_log (service_name, table_name, records_migrated, migration_start, migration_end, status)
-- VALUES ('balance-service', 'balances', (SELECT COUNT(*) FROM balances), NOW(), NOW(), 'completed');

-- =============================================================================
-- POST-MIGRATION CLEANUP
-- =============================================================================

-- Update user counts after migration
UPDATE user_counts uc
SET followers_count = (
    SELECT COUNT(*) FROM follows WHERE following_id = uc.user_id
),
following_count = (
    SELECT COUNT(*) FROM follows WHERE follower_id = uc.user_id
),
posts_count = (
    SELECT COUNT(*) FROM posts WHERE user_id = uc.user_id
),
likes_count = (
    SELECT COUNT(*) FROM post_likes WHERE user_id = uc.user_id
),
updated_at = CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1 FROM users u WHERE u.user_id = uc.user_id
);

-- Update post extra counts
UPDATE post_extra pe
SET views_count = COALESCE((
    SELECT COUNT(*) FROM story_views WHERE story_id = pe.post_id
), 0),
comments_count = (
    SELECT COUNT(*) FROM post_comments WHERE post_id = pe.post_id
),
likes_count = (
    SELECT COUNT(*) FROM post_likes WHERE post_id = pe.post_id
),
saves_count = (
    SELECT COUNT(*) FROM saves WHERE post_id = pe.post_id
),
updated_at = CURRENT_TIMESTAMP
WHERE EXISTS (
    SELECT 1 FROM posts p WHERE p.post_id = pe.post_id
);
