-- V1__initial_schema.sql
-- Flyway migration template for Flash-Spring microservices
-- Customize table names and columns based on service requirements

-- Enable UUID extension if not already enabled
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Example: Users table (customize for your service)
CREATE TABLE IF NOT EXISTS users (
    user_id VARCHAR(36) PRIMARY KEY DEFAULT uuid_generate_v4(),
    username VARCHAR(255) UNIQUE NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    display_name VARCHAR(255),
    bio TEXT,
    profile_picture_url TEXT,
    cover_picture_url TEXT,
    is_verified BOOLEAN DEFAULT FALSE,
    is_private BOOLEAN DEFAULT FALSE,
    is_active BOOLEAN DEFAULT TRUE,
    role VARCHAR(50) DEFAULT 'USER',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_login TIMESTAMP,
    is_online BOOLEAN DEFAULT FALSE
);

-- Example: Create indexes for performance
CREATE INDEX IF NOT EXISTS idx_users_username ON users(username);
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);
CREATE INDEX IF NOT EXISTS idx_users_created_at ON users(created_at);
CREATE INDEX IF NOT EXISTS idx_users_is_verified ON users(is_verified);

-- Example: Audit table (optional - for tracking changes)
CREATE TABLE IF NOT EXISTS audit_log (
    audit_id VARCHAR(36) PRIMARY KEY DEFAULT uuid_generate_v4(),
    user_id VARCHAR(36) NOT NULL,
    action VARCHAR(100) NOT NULL,
    entity_type VARCHAR(100),
    entity_id VARCHAR(36),
    old_value JSONB,
    new_value JSONB,
    ip_address VARCHAR(45),
    user_agent TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_audit_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_audit_user_id ON audit_log(user_id);
CREATE INDEX IF NOT EXISTS idx_audit_entity ON audit_log(entity_type, entity_id);
CREATE INDEX IF NOT EXISTS idx_audit_created_at ON audit_log(created_at);

-- Add comments to tables and columns
COMMENT ON TABLE users IS 'Main users table for authentication and profile management';
COMMENT ON COLUMN users.user_id IS 'Unique identifier for the user (UUID)';
COMMENT ON COLUMN users.password_hash IS 'BCrypt hashed password';
COMMENT ON COLUMN users.role IS 'User role: USER, ADMIN, MODERATOR, SUPPORT';
COMMENT ON TABLE audit_log IS 'Audit trail for tracking user actions and data changes';
