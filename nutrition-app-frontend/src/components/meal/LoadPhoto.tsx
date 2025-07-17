import React, { useState } from 'react';
import { Upload, Button, Image } from 'antd';
import { UploadOutlined, CloseOutlined } from '@ant-design/icons';

const { Dragger } = Upload;

interface ImageUploaderProps {
  onCancel: () => void;
  onImageSelected: (file: File) => void;
}

const ImageUploader: React.FC<ImageUploaderProps> = ({ onCancel, onImageSelected }) => {
  const [imageUrl, setImageUrl] = useState<string | null>(null);

  const handleBeforeUpload = (file: File) => {
    const url = URL.createObjectURL(file);
    setImageUrl(url);
    onImageSelected(file);
    return false;
  };

  return (
    <div style={{ position: 'relative', maxWidth: 600, margin: '0 auto' }}>
      {/* Cancel button top-right */}
      <Button
        icon={<CloseOutlined />}
        onClick={onCancel}
        type="text"
        style={{
          position: 'absolute',
          right: 0,
          top: 0,
          zIndex: 2,
        }}
      />

      <Dragger
        beforeUpload={handleBeforeUpload}
        showUploadList={false}
        accept="image/*"
        style={{
          padding: 20,
          borderRadius: 8,
          background: '#fafafa',
          border: '1px dashed #d9d9d9',
        }}
      >
        <p className="ant-upload-drag-icon">
          <UploadOutlined />
        </p>
        <p className="ant-upload-text">Click or drag an image</p>
      </Dragger>

      {imageUrl && (
        <div style={{ marginTop: 24, textAlign: 'center' }}>
          <Image
            src={imageUrl}
            alt="Preview"
            style={{ maxWidth: '100%', maxHeight: 400 }}
          />
        </div>
      )}
    </div>
  );
};

export default ImageUploader;
