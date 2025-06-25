import React from 'react';
import { Card, message } from 'antd';
import { useNavigate, useLocation } from 'react-router-dom';
import type { UserDetails } from '../types/entities';
import { apiService } from '../services/api';
import UserForm from "../components/user/UserForm";
import { getRedirectedPath } from '../utils/url';

const CreateUserPage: React.FC = () => {
  const navigate = useNavigate();
  const location = useLocation();

  const handleSubmit = async (values: Omit<UserDetails, 'id'>) => {
    try {
      const response = await apiService.createUser(values);

      if (response.body.status === 'OK') {
        message.success('User created successfully');
        navigate(getRedirectedPath(location.search));
      } else {
        const errors = response.body.errors?.map((e) => e.message).join(', ') || 'Failed to create user';
        message.error(errors);
      }
    } catch (error) {
      console.error('Failed to create user:', error);
      message.error('An unexpected error occurred');
    }
  };

  const handleCancel = () => {
    navigate(getRedirectedPath(location.search));
  };

  return (
    <Card title="Create New User" style={{ maxWidth: 600, margin: '0 auto' }}>
      <UserForm onSubmit={handleSubmit} onCancel={handleCancel} />
    </Card>
  );
};

export default CreateUserPage;
