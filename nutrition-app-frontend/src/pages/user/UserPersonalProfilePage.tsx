import React from 'react';
import { useNavigate } from 'react-router-dom';
import UserProfile from '../../components/user/UserProfile.tsx';

const UserPersonalProfilePage: React.FC = () => {
  const navigate = useNavigate();

  return <UserProfile onClose={() => navigate(-1)} />;
};

export default UserPersonalProfilePage;
