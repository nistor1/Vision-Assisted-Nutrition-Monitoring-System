import React, { useEffect, useState } from 'react';
import { Descriptions, Card, Spin, Alert, Button } from 'antd';
import dayjs from 'dayjs';
import type { UserDetails } from '../../types/entities.ts';
import { apiService } from '../../services/api.ts';

interface UserProfileProps {
  userId: string;
  onClose: () => void;
}

const UserProfile: React.FC<UserProfileProps> = ({ userId, onClose }) => {
  const [user, setUser] = useState<UserDetails | null>(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchUser = async () => {
      setLoading(true);
      setError(null);
      try {
        const data = await apiService.getUserById(userId);
        setUser(data.body.payload ?? null);
      } catch (err) {
        setError('Failed to load user details');
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    fetchUser();
  }, [userId]);

  if (loading) return <Spin tip="Loading user..." />;

  if (error) return <Alert message={error} type="error" showIcon />;

  if (!user) return <Alert message="User not found" type="warning" showIcon />;

  return (
    <Card
      title="User Profile"
      extra={<Button onClick={onClose}>Close</Button>}
      style={{ maxWidth: 700, margin: '0 auto' }}
    >
      <Descriptions column={1} bordered>
        <Descriptions.Item label="Username">{user.username}</Descriptions.Item>
        <Descriptions.Item label="Email">{user.email}</Descriptions.Item>
        <Descriptions.Item label="Full Name">{user.fullName}</Descriptions.Item>
        <Descriptions.Item label="Phone Number">{user.phoneNumber}</Descriptions.Item>
        <Descriptions.Item label="Address">{user.address}</Descriptions.Item>
        <Descriptions.Item label="City">{user.city}</Descriptions.Item>
        <Descriptions.Item label="Postal Code">{user.postalCode}</Descriptions.Item>
        <Descriptions.Item label="Country">{user.country}</Descriptions.Item>
        <Descriptions.Item label="Role">{user.role}</Descriptions.Item>
        <Descriptions.Item label="Created At">{dayjs(user.createdAt).format('YYYY-MM-DD HH:mm')}</Descriptions.Item>
      </Descriptions>
    </Card>
  );
};

export default UserProfile;
