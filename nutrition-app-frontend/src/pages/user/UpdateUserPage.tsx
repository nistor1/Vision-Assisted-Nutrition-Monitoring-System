import React, { useEffect, useState } from 'react';
import { useNavigate, useParams, useLocation } from 'react-router-dom';
import { message, Card, Spin, Alert } from 'antd';
import type { UserDetails } from '../../types/UserEntities.ts';
import { apiService } from '../../services/api.ts';
import UserForm from '../../components/user/UserForm.tsx';
import { getRedirectedPath } from '../../utils/url.ts';

const UpdateUserPage: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const location = useLocation();

  const [user, setUser] = useState<UserDetails | null>(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const redirectPath = getRedirectedPath(location.search);

  useEffect(() => {
    const fetchUser = async () => {
      if (!id) return;
      setLoading(true);
      try {
        const res = await apiService.getUserById(id);
        setUser(res.body.payload ?? null);
      } catch (err) {
        setError('Failed to fetch user data');
        console.error(err);
      } finally {
        setLoading(false);
      }
    };
    fetchUser();
  }, [id]);

  const handleSubmit = async (values: UserDetails) => {
    try {
      const response = await apiService.updateUser(values);

      if (response.body.status === 'OK') {
        message.success('User updated successfully');
        navigate(redirectPath);
      } else {
        const errors = response.body.errors?.map((e) => e.message).join(', ') || 'Failed to update user';
        message.error(errors);
      }
    } catch (error) {
      console.error('Update failed:', error);
      message.error('An unexpected error occurred');
    }
  };

  const handleCancel = () => navigate(redirectPath);

  if (loading) return <Spin tip="Loading user..." />;
  if (error) return <Alert type="error" message={error} showIcon />;
  if (!user) return <Alert type="warning" message="User not found" showIcon />;

  return (
    <Card title="Edit User" style={{ maxWidth: 700, margin: '0 auto' }}>
      <UserForm initialValues={user} onSubmit={handleSubmit} onCancel={handleCancel} />
    </Card>
  );
};

export default UpdateUserPage;
