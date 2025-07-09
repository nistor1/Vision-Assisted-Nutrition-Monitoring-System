import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Spin, Alert, message } from 'antd';
import { apiService } from '../../services/api';
import type { UserDetails, UpdateProfileRequest } from '../../types/UserEntities';
import UserPersonalForm from "../../components/user/UserPersonalForm.tsx";

const UserPersonalEditPage: React.FC = () => {
  const navigate = useNavigate();
  const [user, setUser] = useState<UserDetails | null>(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const loadUser = async () => {
      try {
        const response = await apiService.getUserPersonal();
        setUser(response.body.payload ?? null);
      } catch (err) {
        setError('Failed to load profile');
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    loadUser();
  }, []);

  const handleSave = async (data: UpdateProfileRequest) => {
    try {
      const response = await apiService.updateUserProfile(data);

      if (response.body.status === "OK") {
        message.success('Profile updated successfully');
        navigate(-1);

      } else {
        message.error(`Update failed: ${response.message}`);
      }
    } catch (err) {
      console.error(err);
      message.error('Failed to update profile');
    }
  };

  if (loading) return <Spin tip="Loading profile..." />;
  if (error) return <Alert message={error} type="error" showIcon />;
  if (!user) return <Alert message="Profile not found" type="warning" showIcon />;

  return (
    <UserPersonalForm
      user={user}
      onClose={() => navigate(-1)}
      onSave={handleSave}
    />
  );
};

export default UserPersonalEditPage;
