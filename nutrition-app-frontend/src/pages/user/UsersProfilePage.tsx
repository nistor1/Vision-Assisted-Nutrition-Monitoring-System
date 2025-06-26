import React from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import UserProfile from '../../components/user/UserProfile.tsx';

const UserProfilePage: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();

  if (!id) return null;

  return <UserProfile userId={id} onClose={() => navigate(-1)} />;
};

export default UserProfilePage;
