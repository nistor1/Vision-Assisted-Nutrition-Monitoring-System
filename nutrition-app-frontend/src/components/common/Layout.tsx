import React from 'react';
import type { ReactNode } from 'react';
import { Layout } from 'antd';
import { Navbar } from './Navbar';

const { Content } = Layout;

interface MainLayoutProps {
  children: ReactNode;
}

export const MainLayout: React.FC<MainLayoutProps> = ({ children }) => {
  return (
    <Layout style={{ minHeight: '100vh' }}>
      <Navbar />
      <Content style={{ padding: '80px 24px 24px' }}>
        {children}
      </Content>
    </Layout>
  );
};