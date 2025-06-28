import React, { useState, useEffect } from 'react';
import { useSearchParams, useNavigate } from 'react-router-dom';
import { Form, Input, Button, Card, message } from 'antd';
import { apiService } from '../../services/api.ts';
import type {NutritionError} from "../../types/response.ts";
import {useAuth} from "../../context/AuthContext.tsx";

const ResetPassword: React.FC = () => {
  const [searchParams] = useSearchParams();
  const token = searchParams.get('token');
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);

  const { logout } = useAuth();

  useEffect(() => {
    logout();
  }, [logout]);

  const onFinish = async (values: { password: string }) => {
    if (!token) {
      message.error('Missing reset token');
      return;
    }

    setLoading(true);
    try {
      const response = await apiService.resetPassword(token, values.password);

      if (response.body?.status === 'OK') {
        message.success('Password reset successfully');
        navigate('/login');
      } else {
        const errors = (response.body?.errors as NutritionError[] | undefined)
          ?.map((e) => e.message)
          .join(', ') || 'Password reset failed';        message.error(errors);
      }
    } catch (err) {
      console.error('Error resetting password:', err);
      message.error('Unexpected error');
    } finally {
      setLoading(false);
    }
  };

  return (
    <Card title="Reset Password" style={{ maxWidth: 400, margin: '100px auto' }}>
      <Form layout="vertical" onFinish={onFinish}>
        <Form.Item
          name="password"
          label="New Password"
          rules={[{ required: true, message: 'Please enter your new password' }]}
        >
          <Input.Password placeholder="Enter new password" />
        </Form.Item>
        <Form.Item>
          <Button type="primary" htmlType="submit" loading={loading} block>
            Reset Password
          </Button>
        </Form.Item>
      </Form>
    </Card>
  );
};

export default ResetPassword;
