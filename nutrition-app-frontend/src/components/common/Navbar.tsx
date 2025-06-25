import React, { useState, useEffect } from 'react';
import { Layout, Menu, Button, Drawer } from 'antd';
import {
  HomeOutlined,
  DoubleRightOutlined,
  ContactsOutlined,
  MenuOutlined,
  CloseOutlined,
  UserOutlined,
  LogoutOutlined,
  LoginOutlined,
} from '@ant-design/icons';
import { useLocation, useNavigate } from 'react-router-dom';
import { ROUTES } from '../../routes/AppRouter';
import { useAuth } from '../../context/AuthContext';

const { Header } = Layout;

export const Navbar: React.FC = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const { user, logout, isAuthenticated } = useAuth();

  const [mobileMenuVisible, setMobileMenuVisible] = useState(false);
  const [isMobile, setIsMobile] = useState(false);

  useEffect(() => {
    const handleResize = () => {
      const width = window.innerWidth;
      setIsMobile(width < 1024);
      if (width >= 1024) setMobileMenuVisible(false);
    };
    handleResize();
    window.addEventListener('resize', handleResize);
    return () => window.removeEventListener('resize', handleResize);
  }, []);

  const handleMenuClick = ({ key }: { key: string }) => {
    if (key === ROUTES.LOGOUT) {
      logout();
      navigate(ROUTES.LOGIN);
    } else {
      navigate(key);
    }
    setMobileMenuVisible(false);
  };

  const menuItems = isAuthenticated
    ? [
      { key: ROUTES.HOME, icon: <HomeOutlined />, label: 'Home' },
      { key: `${ROUTES.USERS}/${user.id}`, icon: <UserOutlined />, label: 'Profile' },
      { key: ROUTES.CONTACT, icon: <ContactsOutlined />, label: 'Contact' },
      { key: ROUTES.LOGOUT, icon: <LogoutOutlined />, label: '' },
    ]
    : [
      { key: ROUTES.HOME, icon: <HomeOutlined />, label: 'Home' },
      { key: ROUTES.CONTACT, icon: <ContactsOutlined />, label: 'Contact' },
      { key: ROUTES.LOGIN, icon: <LoginOutlined />, label: 'Login' },
      { key: ROUTES.REGISTER, icon: <DoubleRightOutlined />, label: 'Register' },
    ];

  return (
    <>
      <Header
        style={{
          background: '#fff',
          padding: '0 16px',
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'space-between',
          boxShadow: '0 2px 8px rgba(0, 0, 0, 0.06)',
          position: 'sticky',
          top: 0,
          zIndex: 1000,
        }}
      >
        {isMobile && (
          <Button type="text" icon={<MenuOutlined />} onClick={() => setMobileMenuVisible(true)} />
        )}
        <div
          onClick={() => navigate(ROUTES.HOME)}
          style={{ fontSize: '20px', fontWeight: 'bold', cursor: 'pointer', color: '#1677ff' }}
        >
          Nutrition App
        </div>
        {!isMobile && (
          <Menu
            mode="horizontal"
            selectedKeys={[location.pathname]}
            items={menuItems}
            onClick={handleMenuClick}
            style={{ background: 'transparent', border: 'none' }}
          />
        )}
      </Header>

      <Drawer
        title="Navigation"
        placement="left"
        onClose={() => setMobileMenuVisible(false)}
        open={mobileMenuVisible}
        closeIcon={<CloseOutlined />}
      >
        <Menu
          mode="vertical"
          selectedKeys={[location.pathname]}
          items={menuItems}
          onClick={handleMenuClick}
        />
      </Drawer>
    </>
  );
};
