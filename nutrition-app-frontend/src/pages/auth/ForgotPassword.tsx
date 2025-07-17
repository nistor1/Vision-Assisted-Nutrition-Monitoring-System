import React, { useState, useEffect } from 'react';
import { Card, Form, Input, Button, message } from 'antd';
import { useNavigate } from 'react-router-dom';
import { apiService } from '../../services/api.ts';
import { useAuth } from '../../context/AuthContext.tsx';

const ForgotPassword: React.FC = () => {
  const [loading, setLoading] = useState(false);
  const { logout } = useAuth();
  const navigate = useNavigate();

  useEffect(() => {
    logout();
  }, [logout]);

  const onFinish = async (values: { email: string }) => {
    try {
      setLoading(true);
      const response = await apiService.forgotPassword(values.email);
      if (response.body?.status === 'OK') {
        message.success('Email sent successfully');
        navigate('/login');
      } else {
        const errors = (response.body?.errors as { message: string }[] | undefined)
          ?.map((e) => e.message)
          .join(', ') || 'Failed to send reset email';
        message.error(errors);
      }
    } catch (error) {
      console.error(error);
      message.error('Unexpected error occurred');
    } finally {
      setLoading(false);
    }
  };

  return (
    <Card title="Forgot Password" style={{ maxWidth: 400, margin: '80px auto' }}>
      <Form layout="vertical" onFinish={onFinish}>
        <Form.Item
          label="Email"
          name="email"
          rules={[
            { required: true, message: 'Please input your email' },
            { type: 'email', message: 'Invalid email address' }
          ]}
        >
          <Input placeholder="Enter your email" />
        </Form.Item>

        <Form.Item>
          <Button type="primary" htmlType="submit" loading={loading} block>
            Send Reset Link
          </Button>
        </Form.Item>
      </Form>
    </Card>
  );
};

export default ForgotPassword;
